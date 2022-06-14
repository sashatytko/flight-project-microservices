package com.pdp.order.config;

import com.pdp.order.service.OrderService;
import common.model.payment.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class OrderConfig {

	@Autowired
	OrderService orderService;

	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClient() {
		return WebClient.builder();
	}

	@Bean
	public Sinks.Many<Transaction> orderSinks(){
		return Sinks.many().multicast().onBackpressureBuffer();
	}

	@Bean
	public Supplier<Flux<Transaction>> transactionProducer(Sinks.Many<Transaction> transactions){
		return transactions::asFlux;
	}
}
