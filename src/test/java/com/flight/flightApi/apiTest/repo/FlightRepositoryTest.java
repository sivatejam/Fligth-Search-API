package com.flight.flightApi.apiTest.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.flight.flightApi.Entity.Flight;
import com.flight.flightApi.repository.FlightRepository;

@DataJpaTest
public class FlightRepositoryTest {

	@Autowired
	private FlightRepository repo;

	@Test
	public void testFindByOriginAndDestination(){
		Flight flight1 = new Flight(1L,"A101","AMS","DEL",LocalDateTime.of(2023, 8, 2, 11, 0),LocalDateTime.of(2023, 8, 2, 17, 0), 850.00);
		Flight flight2 = new Flight(2L,"B101","AMS","BOM",LocalDateTime.of(2023, 8, 2, 12, 0),LocalDateTime.of(2023, 8, 2, 19, 30),750.00);
		Flight flight3 = new Flight(3L,"C101","AMS","BLR",LocalDateTime.of(2023, 8, 2, 13, 0),LocalDateTime.of(2023, 8, 2, 18, 30),800.00);

		repo.save(flight1);
		repo.save(flight2);
		repo.save(flight3);
		List<Flight> flightNew = this.repo.findByOriginAndDestination(flight1.getOrigin(),flight1.getDestination());

		assertThat(flightNew).size().isNotNull();
	}
}
