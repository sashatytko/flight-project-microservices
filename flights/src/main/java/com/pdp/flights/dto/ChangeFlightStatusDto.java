package com.pdp.flights.dto;

import common.model.flight.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChangeFlightStatusDto {
	private String flightId;
	private FlightStatus flightStatus;
}
