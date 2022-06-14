package com.pdp.flights.service.scheduledflight;

import com.pdp.flights.dao.ScheduledFlightRepository;
import com.pdp.flights.dto.ChangeFlightStatusDto;
import com.pdp.redis.RedisManager;
import common.model.flight.ScheduledFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduledFlightService implements IsScheduledFlightService{

	@Autowired
	private ScheduledFlightRepository scheduledFlightRepository;


	@Override
	public void save(ScheduledFlight flight) {
		scheduledFlightRepository.save(flight);
	}

	@Override
	public ScheduledFlight readById(String flightId) {
		return scheduledFlightRepository.findById(flightId).orElse(null);
	}

	@Override
	public void changeFlightStatus(ChangeFlightStatusDto changeFlightStatusDto) {
		Optional<ScheduledFlight> flight = scheduledFlightRepository.findById(changeFlightStatusDto.getFlightId());
		if (flight.isPresent()) {
			ScheduledFlight scheduledFlight = flight.get();
			scheduledFlight.setFlightStatus(changeFlightStatusDto.getFlightStatus());
			scheduledFlightRepository.save(scheduledFlight);
			RedisManager
					.getRedis()
					.xadd("flight-change-status", scheduledFlight.getId() +" -> " + scheduledFlight.getFlightStatus());
		}
	}
}
