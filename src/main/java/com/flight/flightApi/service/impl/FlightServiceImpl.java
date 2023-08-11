package com.flight.flightApi.service.impl;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.flight.flightApi.Entity.Flight;
import com.flight.flightApi.Exception.FlightDataNotFoundException;
import com.flight.flightApi.dto.FlightDto;
import com.flight.flightApi.enumaration.SortOrder;
import com.flight.flightApi.repository.FlightRepository;
import com.flight.flightApi.service.FlightService;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceImpl.class);

	@NonNull
	private  final FlightRepository flightRepository;

	List<FlightDto> flightsListDto;



	/**

	 * This method will fetch data based on origin and destination

	 * @param origin

	 * @Param destination

	 * @return List of Flight

	 * @exception Data Not Found

	 */
	@Override
	public List<FlightDto> flightsList(String origin, String destination) {
		List<Flight> list = flightRepository.findByOriginAndDestination(origin, destination);

		if(list.isEmpty()) {
			throw new FlightDataNotFoundException("Fligth Data is Not Found");
		}
		LOGGER.info("Flight Data fetching based on origin and destination");
		return list.stream().map(flight->mapToDto(flight)).collect(Collectors.toList());
	}

	/**

	 * This method will first Sort Flight List By Price then Duration

	 * @param priceSort

	 * @param durationSort

	 * @param flightList

	 * @return List of Flight

	 */
	@Override
	public List<FlightDto> sortFlights(List<FlightDto> flightDto,SortOrder priceSort,SortOrder durationSort) {
	
		flightsListDto = flightDto;// priceSort is null or not, duratonSort
		flightsListDto = sortByPrice(priceSort);
		flightsListDto = sortByDuration(durationSort,priceSort);
		return flightsListDto;
	}

	/**

	 * This method will first Sort Flight List By Price based on order type

	 * @param priceSort

	 * @return List of Flight

	 */
	private List<FlightDto> sortByPrice(SortOrder priceSort){
		if(priceSort != null ) {
			if(priceSort.ASC.equals(priceSort)) {
				flightsListDto.sort(Comparator.comparingDouble(FlightDto::getPrice));
			}else if(priceSort.DESC.equals(priceSort)) {
				flightsListDto.sort(Comparator.comparingDouble(FlightDto::getPrice).reversed());
			}
		}
		LOGGER.info("Flight list sorted based on Price and order type");
		return flightsListDto;
	}

	/**

	 * This method will first Sort Flight List By Duration based on order type

	 * @param priceSort

	 * @param durationSort

	 * @return List of Flight

	 */
	private List<FlightDto> sortByDuration(SortOrder durationSort,SortOrder priceSort){

		if(durationSort != null ) {
			if(priceSort==null) { 
				if(durationSort.ASC.equals(durationSort)) {
					flightsListDto.sort(Comparator.comparing(flight->Duration.between(flight.getDepartureDateTime(),flight.getArrivalDateTime())));
				}else if(durationSort.DESC.equals(durationSort)) {
					flightsListDto.sort(Comparator.comparing(flight->Duration.between(((FlightDto) flight).getDepartureDateTime(),((FlightDto) flight).getArrivalDateTime())).reversed());
				}
				LOGGER.info("Flight list sorted based on Duration and order type");
			}else {
				if(priceSort.ASC.equals(priceSort)) {
					if(durationSort.ASC.equals(durationSort)) {
						flightsListDto.sort(Comparator.comparingDouble(FlightDto::getPrice)
								.thenComparing(Comparator.comparing(flight->Duration.between(flight.getDepartureDateTime(),flight.getArrivalDateTime()))));
					}else if(durationSort.DESC.equals(durationSort)) {
						flightsListDto.sort(Comparator.comparingDouble(FlightDto::getPrice)
								.thenComparing(Comparator.comparing(flight->Duration.between(((FlightDto) flight).getDepartureDateTime(),((FlightDto) flight).getArrivalDateTime()))
										.reversed()));
					}
				}
				else if(priceSort.DESC.equals(priceSort)) {
					if(durationSort.ASC.equals(durationSort)) {
						flightsListDto.sort(Comparator.comparingDouble(FlightDto::getPrice).reversed()
								.thenComparing(Comparator.comparing(flight->Duration.between(flight.getDepartureDateTime(),flight.getArrivalDateTime()))));
					}else if (durationSort.DESC.equals(durationSort)) {
						flightsListDto.sort(Comparator.comparingDouble(FlightDto::getPrice)
								.thenComparing(Comparator.comparing(flight->Duration.between(flight.getDepartureDateTime(),flight.getArrivalDateTime())))
								.reversed());
					}
				}
			}
		}
		LOGGER.info("Flight list sorted based on Price and Duration with their order type");
		return flightsListDto;
	}


	private FlightDto mapToDto(Flight flight) { 
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



