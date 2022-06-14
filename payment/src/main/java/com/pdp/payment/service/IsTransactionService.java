package com.pdp.payment.service;

import common.dto.payment.TransactionResponse;
import common.model.payment.Transaction;
import org.springframework.messaging.Message;

public interface IsTransactionService {
	void createTransaction(Transaction transaction);
	Transaction getTransactionById(String transactionId);
	TransactionResponse processTransaction(Message<Transaction> transaction);
}
