package com.flight.flightApi.dto;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class FlightDto {
	
	private Long id;
	
	private String flightNumber;
	
	private String origin;
	
	private String destination;
	
	private LocalDateTime departureDateTime;
	
	private LocalDateTime arrivalDateTime;
	
	private Double price;

	
}
