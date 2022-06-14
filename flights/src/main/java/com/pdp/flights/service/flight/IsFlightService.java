package com.pdp.flights.service.flight;

import common.model.flight.Flight;

public interface IsFlightService {
	void save(Flight flight);
	Flight readById(String flightId);
}
