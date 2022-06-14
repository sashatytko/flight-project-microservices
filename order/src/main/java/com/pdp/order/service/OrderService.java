package com.pdp.order.service;

import com.pdp.order.dao.OrderRepository;
import com.pdp.order.dto.OrderRequestDto;
import com.pdp.order.utils.OrderUtils;
import com.pdp.redis.RedisManager;
import com.pdp.redis.RedisPrefix;
import common.dto.payment.BalanceRequest;
import common.dto.payment.GetBalanceResponse;
import common.dto.payment.TransactionResponse;
import common.model.flight.FlightStatus;
import common.model.flight.ScheduledFlight;
import common.model.order.Order;
import common.model.order.OrderStatus;
import common.model.payment.Transaction;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
@RefreshScope
public class OrderService implements IsOrderService {

	@Value("${kafka.request.topic}")
	private String requestTopic;

	@Value("${kafka.reply.topic}")
	private String requestReplyTopic;

	@Autowired
	private OrderUtils orderUtils;

	@Autowired
	private WebClient.Builder webClientBuilder;

	private final Double TICKET_PRICE = 4000d;

	@Autowired
	ReplyingKafkaTemplate<String, BalanceRequest, GetBalanceResponse> kafkaGetBalanceTemplate;

	@Autowired
	private Sinks.Many<Transaction> transactionSinks;

	@Value("${microservice.flight-service.get-scheduled-flight-by-id}")
	private String SCHEDULED_FLIGHT_BY_ID_URI;

	private final Logger LOG = Logger.getLogger(OrderService.class.getName());

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void createOrder(OrderRequestDto order) {
		Order orderRequest = orderUtils.orderRequestDtoToOrder(order);
		double balance = getBalance(order.getCardNumber());
		String transactionId = UUID.randomUUID().toString();
		orderRequest.setTransactionId(transactionId);
		LOG.info("balance: " + balance + "\n");
		boolean isAvailable = webClientBuilder.build().get().uri(SCHEDULED_FLIGHT_BY_ID_URI + order.getScheduledFlightId()).retrieve().bodyToMono(ScheduledFlight.class)
				.block()
				.getFlightStatus() != FlightStatus.CANCELLED;
		if (balance >= TICKET_PRICE && isAvailable) {
			Sinks.EmitResult emitResult = transactionSinks.tryEmitNext(
					Transaction.builder().id(transactionId).card(order.getCardNumber())
							.amount(TICKET_PRICE).build());
			orderRequest.setOrderStatus(OrderStatus.UNCONFIRMED);
		}
		orderRepository.save(orderRequest);
		addOrderToCache(orderRequest);
	}

	private void addOrderToCache(Order order) {
		RedisCommands<String, String> redis = RedisManager.getRedis();
		StatefulRedisConnection<String, String> statefulConnection = redis.getStatefulConnection();
		statefulConnection.setAutoFlushCommands(false);
		RedisAsyncCommands<String, String> sync = statefulConnection.async();
		String key = RedisPrefix.ORDER.getPrefix() + order.getId();
		sync.hset(key, Map.of(
				"id", String.valueOf(order.getId()),
				"orderStatus", order.getOrderStatus().toString(),
				"passengerName", order.getPassengerName(),
				"transactionId", String.valueOf(order.getTransactionId())
		));
		sync.expire(key, Duration.of(500, ChronoUnit.SECONDS));
		statefulConnection.flushCommands();
	}

	private double getBalance(String cardNumber) {
		ProducerRecord<String, BalanceRequest> record = new ProducerRecord<>(requestTopic, BalanceRequest.builder().cardNumber(cardNumber).build());
		/*record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));*/
		RequestReplyFuture<String, BalanceRequest, GetBalanceResponse> result = kafkaGetBalanceTemplate
				.sendAndReceive(record);
		try {
			ConsumerRecord<String, GetBalanceResponse> stringGetBalanceResponseConsumerRecord = result.get();
			return stringGetBalanceResponseConsumerRecord.value().getBalance();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Order getOrderById(String id) {
		return orderRepository.findById(id).orElse(null);
	}

	@Override
	public void confirmOrderByTransaction(TransactionResponse transaction) {
		LOG.info("CONFIRMED :" + transaction.getId());
		Order order = orderRepository.getByTransactionId(transaction.getId());
		order.setOrderStatus(OrderStatus.PAYED);
		orderRepository.save(order);
	}
}
