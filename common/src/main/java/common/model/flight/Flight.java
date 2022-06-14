package common.model.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Flight {
	private String id;
	private String flightNo;
	private String departureAirport;
	private String arrivalAirport;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
}
