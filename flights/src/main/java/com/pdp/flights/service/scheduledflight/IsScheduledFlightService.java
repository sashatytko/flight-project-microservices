package com.pdp.flights.service.scheduledflight;

import com.pdp.flights.dto.ChangeFlightStatusDto;
import common.model.flight.ScheduledFlight;

public interface IsScheduledFlightService {
	void save(ScheduledFlight flight);
	ScheduledFlight readById(String flightId);
	void changeFlightStatus(ChangeFlightStatusDto changeFlightStatusDto);
}
