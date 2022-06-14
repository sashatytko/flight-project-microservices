package common.model.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScheduledFlight {
	private String id;
	private String flightId;
	private String aircraft;
	private FlightStatus flightStatus;
	private LocalDate flightDate;
}
