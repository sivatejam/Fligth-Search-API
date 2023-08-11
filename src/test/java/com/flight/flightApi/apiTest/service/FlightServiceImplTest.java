package com.flight.flightApi.apiTest.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.flight.flightApi.Entity.Flight;
import com.flight.flightApi.dto.FlightDto;
import com.flight.flightApi.enumaration.SortOrder;
import com.flight.flightApi.repository.FlightRepository;
import com.flight.flightApi.service.impl.FlightServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {

	@Mock
	private FlightRepository repo;

	@Autowired
	SortOrder sortOrder;

	List<Flight> flightList;
	List<FlightDto> flightListDto;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		Flight flight1 = new Flight(1L,"A101","AMS","DEL",LocalDateTime.of(2023, 8, 2, 11, 0),LocalDateTime.of(2023, 8, 2, 17, 0), 850.00);
		Flight flight2 = new Flight(2L,"B101","AMS","DEL",LocalDateTime.of(2023, 8, 2, 12, 0),LocalDateTime.of(2023, 8, 2, 19, 30),750.00);
		Flight flight3 = new Flight(3L,"C101","AMS","DEL",LocalDateTime.of(2023, 8, 2, 13, 0),LocalDateTime.of(2023, 8, 2, 18, 30),800.00);
		flightList = new ArrayList<>();
		flightList.add(flight1);
		flightList.add(flight2);
		flightList.add(flight3);
	}

	@Test
	public void testFindFlightsList() {
		FlightServiceImpl impl = new FlightServiceImpl(repo);
		Mockito.when(repo.findByOriginAndDestination(anyString(), anyString())).thenReturn(flightList);
		List<FlightDto> existingFlight =impl.flightsList("AMS","DEL");
		assertNotNull(existingFlight);
		assertEquals(3,existingFlight.size());
	}
	
	
	@Test
	public void testFindFlightsWithAsscendingPriceDescendingDuration() { 		
		FlightServiceImpl impl = new FlightServiceImpl(repo);
		lenient().when(repo.findByOriginAndDestination(anyString(), anyString())).thenReturn(flightList);
		flightListDto =  flightList.stream().map(flight->mapToDto(flight)).collect(Collectors.toList());
		List<FlightDto> existingFlight =impl.sortFlights(flightListDto,SortOrder.ASC,SortOrder.DESC);
		assertNotNull(existingFlight);
		assertEquals(3,existingFlight.size());
		assertEquals(750.00,existingFlight.get(0).getPrice());
		assertEquals(800.00,existingFlight.get(1).getPrice());
		assertEquals(850.00,existingFlight.get(2).getPrice());

	}
	
	public FlightDto mapToDto(Flight flight) { 
		FlightDto dto = new FlightDto();
		dto.setId(flight.getId()); 
		dto.setFlightNumber(flight.getFlightNumber());
		dto.setOrigin(flight.getOrigin());
		dto.setDestination(flight.getDestination());
		dto.setDepartureDateTime(flight.getDepartureDateTime());
		dto.setArrivalDateTime(flight.getArrivalDateTime()); 
		dto.setPrice(flight.getPrice());
		return dto;
	}


}
