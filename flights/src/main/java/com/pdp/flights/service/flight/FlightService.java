package com.pdp.flights.service.flight;

import com.pdp.flights.dao.FlightRepository;
import com.pdp.redis.RedisManager;
import common.model.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService implements IsFlightService {

	@Autowired
	private FlightRepository flightRepository;

	@Override
	public void save(Flight flight) {
		flightRepository.save(flight);
		RedisManager
				.getRedis()
				.publish("flight-new", flight.getDepartureAirport() +" -> " + flight.getArrivalAirport() + "(" + flight.getFlightNo() +") appeared!");
	}

	@Override
	public Flight readById(String flightId) {
		return flightRepository.findById(flightId).orElse(null);
	}
}
