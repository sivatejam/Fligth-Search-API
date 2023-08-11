package com.flight.flightApi.Exception;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(FlightDataNotFoundException.class)
	public ResponseEntity<String> handleDataNotFound(FlightDataNotFoundException flightDataNotFoundException){
		return new ResponseEntity<String>(flightDataNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){
		return new ResponseEntity<String>("Enter valid data",HttpStatus.BAD_REQUEST);
	}
	


	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<String> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException invalidDataAccessResourceUsageException){
		return new ResponseEntity<String>("Enter valid Origin and Destination",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
