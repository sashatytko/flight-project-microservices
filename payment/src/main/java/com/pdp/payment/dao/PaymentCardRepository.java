package com.pdp.payment.dao;

import common.model.payment.PaymentCard;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentCardRepository extends MongoRepository<PaymentCard, Long> {
	Optional<PaymentCard> getByCard(String cardNumber);
}
