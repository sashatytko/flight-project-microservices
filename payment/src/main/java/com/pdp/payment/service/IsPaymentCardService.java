package com.pdp.payment.service;

import common.model.payment.Transaction;

public interface IsPaymentCardService {
	double getAmountByCardNumber(String cardNumber);
	void charge(Transaction payload);
}
