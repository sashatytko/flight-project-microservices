package com.pdp.payment.service;

import com.pdp.payment.dao.TransactionRepository;
import common.dto.payment.TransactionResponse;
import common.model.payment.Transaction;
import common.model.payment.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class TransactionService implements IsTransactionService {

	private final Logger LOG = Logger.getLogger(TransactionService.class.getName());

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PaymentCardService paymentCardService;

	@Override
	public void createTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
	}

	@Override
	public Transaction getTransactionById(String transactionId) {
		return transactionRepository.findById(transactionId).orElse(null);
	}

	@Override
	public TransactionResponse processTransaction(Message<Transaction> transaction) {
		LOG.info(String.valueOf(transaction.getPayload().getAmount()));
		Acknowledgment acknowledgment = transaction.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		Transaction payload = transaction.getPayload();
		if (acknowledgment == null) {
			return toTransactionResponse(payload);
		}
		try {

			paymentCardService.charge(payload);
			payload.setTransactionStatus(TransactionStatus.SUCCESSFUL);
			acknowledgment.acknowledge();

		} catch (RuntimeException e) {
			payload.setTransactionStatus(TransactionStatus.FAILED);
		}
		transactionRepository.save(payload);
		return toTransactionResponse(payload);
	}

	private TransactionResponse toTransactionResponse(Transaction transaction) {
		return TransactionResponse.builder().transactionStatus(transaction.getTransactionStatus())
				.amount(transaction.getAmount())
				.id(transaction.getId())
				.card(transaction.getCard())
				.build();
	}
}
