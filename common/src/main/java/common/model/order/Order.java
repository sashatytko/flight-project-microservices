package common.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
	private String id;
	private String scheduledFlightId;
	private String passengerName;
	private OrderStatus orderStatus;
	private String transactionId;
	private LocalDateTime issuedDate;
}
