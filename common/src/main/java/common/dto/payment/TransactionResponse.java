package common.dto.payment;

import common.model.payment.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionResponse {
	private String id;
	private double amount;
	private String card;
	private TransactionStatus transactionStatus;
}
