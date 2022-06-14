package com.pdp.order.utils;

import com.pdp.order.dto.OrderRequestDto;
import common.model.order.Order;
import common.model.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderUtils {

	public Order orderRequestDtoToOrder(OrderRequestDto orderRequestDto) {
		return Order.builder()
				.orderStatus(OrderStatus.CANCELLED)
				.passengerName(orderRequestDto.getPassengerName())
				.scheduledFlightId(orderRequestDto.getScheduledFlightId())
				.issuedDate(LocalDateTime.now())
				.build();
	}
}
