package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Represents an overall booking of all types of ticket. This includes validation that prevents invalid states.
 *
 * Although this would require updating if new types of tickets were added, this trade-off avoids the need to handle
 * maps of values throughout the codebase and the complexity of handling potentially missing values.
 *
 * @param adults The number of adult tickets
 * @param children The number of child tickets
 * @param infants The number of infant tickets
 */
public record BookingRequest(int adults, int children, int infants) {

    private static final String ERROR_NEGATIVE_ADULTS = "Number of adults cannot be negative.";
    private static final String ERROR_NEGATIVE_CHILDREN = "Number of children cannot be negative.";
    private static final String ERROR_NEGATIVE_INFANTS = "Number of infants cannot be negative.";
    private static final String ERROR_NO_TICKETS = "Booking must include at least one ticket.";

    public BookingRequest {
        if (adults < 0) throw new InvalidBookingRequestException(ERROR_NEGATIVE_ADULTS);
        if (children < 0) throw new InvalidBookingRequestException(ERROR_NEGATIVE_CHILDREN);
        if (infants < 0) throw new InvalidBookingRequestException(ERROR_NEGATIVE_INFANTS);
        if (adults + children + infants <= 0) throw new InvalidBookingRequestException(ERROR_NO_TICKETS);
    }

    /**
     * Combines one or more ticket requests into a single booking request that includes all tickets
     * @param requests The requested tickets
     * @return The combined booking request
     */
    public static BookingRequest fromTicketTypeRequests(TicketTypeRequest... requests) {
        if (requests == null) {
            throw new InvalidBookingRequestException("TicketTypeRequest cannot be null.");
        }

        Map<TicketTypeRequest.Type, Integer> groupedTickets = Arrays.stream(requests).collect(
                groupingBy(
                        TicketTypeRequest::getTicketType,
                        Collectors.summingInt(TicketTypeRequest::getNoOfTickets) // Required in case a single type is repeated
                )
        );

        return new BookingRequest(
                groupedTickets.getOrDefault(TicketTypeRequest.Type.ADULT, 0),
                groupedTickets.getOrDefault(TicketTypeRequest.Type.CHILD, 0),
                groupedTickets.getOrDefault(TicketTypeRequest.Type.INFANT, 0)
        );
    }

}
