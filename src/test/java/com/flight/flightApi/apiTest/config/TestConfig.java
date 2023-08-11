package com.flight.flightApi.apiTest.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flight.flightApi.enumaration.SortOrder;
import com.flight.flightApi.repository.FlightRepository;
import com.flight.flightApi.service.FlightService;
import com.flight.flightApi.service.impl.FlightServiceImpl;

@Configuration
public class TestConfig {


	@Bean
	public FlightService service() {
		return new FlightServiceImpl(repo()); 
	}

	
	@Bean
	public ModelMapper model() {
		return new ModelMapper();
	}

	
	@Bean
	public SortOrder sortOrder() {
		return null;
	}
	
	@Bean
	public FlightRepository repo() {
		return null;
	}


}

