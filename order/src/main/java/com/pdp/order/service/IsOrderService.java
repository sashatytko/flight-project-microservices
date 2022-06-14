package com.pdp.order.service;

import com.pdp.order.dto.OrderRequestDto;
import common.dto.payment.TransactionResponse;
import common.model.order.Order;
import common.model.payment.Transaction;

public interface IsOrderService {
	void createOrder(OrderRequestDto order);
	Order getOrderById(String id);
	void confirmOrderByTransaction(TransactionResponse transactionId);
}
