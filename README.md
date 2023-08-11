# Flight Search API
The Flight Search API allows you to find a list of flights between given origin and destination,
with options to sort the results by price and duration.

## Table of Contents
* Introduction
* Features
* Prerequisites
* API Endpoints

### Introduction
The Flight Search API is a Spring Boot application that helps you search for flights based on various parameters
such as origin, destination, price sorting order, andduration sorting order.It calculates the duration of each flight
using departure and arrival times and provides a sorted list of flights.

### Features
* Search for flights based on origin and destination.
* Sort flights by price and/or duration.
* Calculate flight duration using departure and arrival times.

### Prerequisites
* Java Development Kit(JDK)8 or higher
* Mavan
* Integrated Development Environment(IDE)(Eclipse/STS/IntelliJ IDEA)
### API Endpoints
* GET '/flights/api/all/${origin}/${destination}'
    * 'priceSort'(optional): Sort order for prices.
    * 'durationSort'(optional): Sort order for durations.
# Fligth-Search-API
