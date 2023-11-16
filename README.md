# cinema-tickets

An application for booking cinema tickets based upon the number and type of tickets requested. Requests are subject to a number of business rules.

## Validation and Business rules

The following rules are enforced by the `BookingRequest` class to make it impossible to represent states that do not make sense:

- Bookings must include at least one ticket
- Bookings cannot include a negative number of adult, child or infant tickets

To enable future flexibility, these are separated from a range of business rules that are applied, which may need to become conditional or configurable (e.g., adding a rule that children and infants cannot be added to screenings that start after 10pm). The default implemented validation rules are:

- Bookings must have at least one adult
- Bookings must include at least one adult per infant
- Bookings may not include more than 20 tickets

## Entry points

The overall service is implemented by the `TicketServiceImpl` class and accessed via the `purchaseTickets` method, which requires an account ID and requests for one or more tickets.

## Documentation and testing

The behaviour of the service is demonstrated via unit tests, which have full coverage (excluding the `thirdparty` package). Javadoc is provided to improve discoverability.

## Requirements

This service is built to run with Java 17 and requires features from this version. It can be built using Maven and has test-scoped dependencies upon JUnit 5 and Mockito.
