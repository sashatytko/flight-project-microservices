package com.pdp.order.config;

import com.pdp.order.service.OrderService;
import common.dto.payment.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class TransactionConsumer {

	@Autowired
	OrderService orderService;

	/*@Bean
	Consumer<TransactionResponse> confirmOrderBySuccessTransaction() {
		return transaction -> orderService.confirmOrderByTransaction(transaction);
	}*/
}
