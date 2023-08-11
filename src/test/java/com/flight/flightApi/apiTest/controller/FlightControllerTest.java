package com.flight.flightApi.apiTest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.flight.flightApi.Entity.Flight;
import com.flight.flightApi.controller.FlightController;
import com.flight.flightApi.dto.FlightDto;
import com.flight.flightApi.enumaration.SortOrder;
import com.flight.flightApi.service.FlightService;

@EnableWebMvc
@ExtendWith(MockitoExtension.class)
@WebMvcTest(FlightController.class)
public class FlightControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FlightService service;

	List<Flight> flightList;




	@BeforeEach
	public void setup() {
		Flight flight1 = new Flight(1L,"F101","BOM","DEL",LocalDateTime.of(2023, 8, 2, 20, 30),LocalDateTime.of(2023, 8, 2, 21, 30),80.00);
		Flight flight2 = new Flight(2L,"G101","BOM","DEL",LocalDateTime.of(2023, 8, 2, 18, 00),LocalDateTime.of(2023, 8, 2, 19, 30),100.00);
		Flight flight3 = new Flight(3L,"F201","BOM","DEL",LocalDateTime.of(2023, 8, 2, 21, 15),LocalDateTime.of(2023, 8, 2, 22, 30),80.00);
		Flight flight4 = new Flight(4L,"G01","BOM","DEL",LocalDateTime.of(2023, 8, 2, 20, 20),LocalDateTime.of(2023, 8, 2, 21, 30),100.00);
		flightList = new ArrayList<>();
		flightList.add(flight1);
		flightList.add(flight2);
		flightList.add(flight3);
		flightList.add(flight4);
	}

	@Autowired
	private ModelMapper model;

	@Test
	public void testFindFlightsList() throws Exception {
		List<FlightDto> listDto = flightList.stream().map(flight-> model.map(flight, FlightDto.class)).collect(Collectors.toList());
		Mockito.when(service.flightsList(anyString(), anyString())).thenReturn(listDto);
		List<FlightDto> existingFlight =service.flightsList("AMS","DEL");

		mockMvc.perform(MockMvcRequestBuilders.get("/flight/api/all/AMS/DEL"))
		.andExpect(status().isOk());		
		assertNotNull(existingFlight);
		assertEquals(4,existingFlight.size()); 
	}

	@Test
	public void testFightsListAfterSorting() throws Exception {
		List<FlightDto> listDto = flightList.stream().map(flight-> model.map(flight, FlightDto.class)).collect(Collectors.toList());
		Mockito.when(service.sortFlights(any(),any(),any())).thenReturn(listDto);
		mockMvc.perform(MockMvcRequestBuilders.get("/flight/api/all/AMS/DEL")
				.queryParam("priceSort", SortOrder.ASC.name())
				.queryParam("durationSort", SortOrder.DESC.name()))

		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].flightNumber").value("F101"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.[0].price", Matchers.is(listDto.get(0).getPrice())));
		Assertions.assertNotNull(listDto.get(0));
	}
}
