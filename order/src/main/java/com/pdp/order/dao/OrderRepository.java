package com.pdp.order.dao;

import common.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
	Order getByTransactionId(String transactionId);
}
