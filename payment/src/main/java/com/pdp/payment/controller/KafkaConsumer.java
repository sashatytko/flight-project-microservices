package com.pdp.payment.controller;

import com.pdp.payment.service.PaymentCardService;
import com.pdp.payment.service.TransactionService;
import common.dto.payment.BalanceRequest;
import common.dto.payment.GetBalanceResponse;
import common.dto.payment.TransactionResponse;
import common.model.payment.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class KafkaConsumer {

	@Autowired
	private PaymentCardService paymentCardService;

	@Autowired
	private TransactionService transactionService;

	@KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
	@SendTo("${kafka.reply.topic}")
	public GetBalanceResponse getBalanceByCard(BalanceRequest request) {
		System.out.println(request.getCardNumber());
		return GetBalanceResponse.builder().balance(paymentCardService.getAmountByCardNumber(request.getCardNumber())).build();
	}

	@Bean
	Function<Message<Transaction>, TransactionResponse> createTransactionFromKafka() {
		return transaction -> transactionService.processTransaction(transaction);
	}
}
