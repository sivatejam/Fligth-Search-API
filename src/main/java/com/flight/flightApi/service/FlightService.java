package com.flight.flightApi.service;

import java.util.List;

import com.flight.flightApi.dto.FlightDto;
import com.flight.flightApi.enumaration.SortOrder;

public interface FlightService {
	
	List<FlightDto> sortFlights(List<FlightDto> flightDto,SortOrder priceSort,SortOrder durationSort);

	List<FlightDto> flightsList(String origin,String destination);
	

}
