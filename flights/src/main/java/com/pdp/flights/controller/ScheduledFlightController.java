package com.pdp.flights.controller;

import com.pdp.flights.dto.ChangeFlightStatusDto;
import com.pdp.flights.service.flight.IsFlightService;
import com.pdp.flights.service.scheduledflight.IsScheduledFlightService;
import common.model.flight.Flight;
import common.model.flight.ScheduledFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/scheduled-flight")
public class ScheduledFlightController {

	@Autowired
	private IsScheduledFlightService flightService;

	@PostMapping
	public void saveFlight(ScheduledFlight flight) {
		flightService.save(flight);
	}

	@GetMapping("/{id}")
	public ScheduledFlight getFlightById(@PathVariable String id) {
		return flightService.readById(id);
	}

	@PostMapping("/status")
	public void changeFlightStatus(@RequestBody ChangeFlightStatusDto changeFlightStatusDto) {
		flightService.changeFlightStatus(changeFlightStatusDto);
	}

}
