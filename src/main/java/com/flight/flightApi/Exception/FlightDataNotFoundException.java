package com.flight.flightApi.Exception;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class FlightDataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;


	public FlightDataNotFoundException() {

	}
	public FlightDataNotFoundException(String msg) {
		super(msg);
		this.message=msg;
		
	}

}
