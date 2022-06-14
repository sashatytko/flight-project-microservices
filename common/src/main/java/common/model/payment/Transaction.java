package common.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transaction {
	private String id;
	private double amount;
	private String card;
	private TransactionStatus transactionStatus;
}
