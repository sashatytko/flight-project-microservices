package com.pdp.flights.dao;

import common.model.flight.ScheduledFlight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduledFlightRepository extends MongoRepository<ScheduledFlight, String> {
	ScheduledFlight getByFlightId(String flightId);
}
