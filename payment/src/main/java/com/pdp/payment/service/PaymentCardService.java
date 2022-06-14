package com.pdp.payment.service;

import com.pdp.payment.dao.PaymentCardRepository;
import common.model.payment.PaymentCard;
import common.model.payment.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentCardService implements IsPaymentCardService {

	@Autowired
	private PaymentCardRepository paymentCardRepository;

	@Override
	public double getAmountByCardNumber(String cardNumber) {
		return paymentCardRepository.getByCard(cardNumber).orElse(PaymentCard.builder().build()).getAmount();
	}

	@Override
	public void charge(Transaction transaction) {
		paymentCardRepository.getByCard(transaction.getCard())
				.ifPresentOrElse(paymentCard -> {
					if (paymentCard.getAmount() < transaction.getAmount()) {
						insufficientFundsException();
					}
					paymentCard.setAmount(paymentCard.getAmount() - transaction.getAmount());
					paymentCardRepository.save(paymentCard);
				}, this::noSuchCardException);
	}

	private void insufficientFundsException() {
		throw new RuntimeException("Insufficient Funds!");
	}

	private void noSuchCardException() {
		throw new RuntimeException("No card with provided number!");
	}
}
