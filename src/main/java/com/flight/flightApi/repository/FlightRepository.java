package com.flight.flightApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flight.flightApi.Entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
	

	List<Flight> findByOriginAndDestination(String origin, String destination );

}
