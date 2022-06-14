package com.pdp.order.controller;

import com.pdp.order.dto.OrderRequestDto;
import com.pdp.order.service.OrderService;
import common.model.order.Order;
import common.model.payment.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable String id) {
		return orderService.getOrderById(id);
	}

	@PostMapping("")
	public void createOrder(@RequestBody OrderRequestDto order) {
		orderService.createOrder(order);
	}
}
