package com.pdp.flights.controller;

import com.pdp.flights.service.flight.IsFlightService;
import common.model.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/flight")
public class FlightController {

	@Autowired
	private IsFlightService flightService;

	@PostMapping
	public void saveFlight(Flight flight) {
		flightService.save(flight);
	}

	@GetMapping("/{id}")
	public Flight getFlightById(@PathVariable String id) {
		return flightService.readById(id);
	}

}
