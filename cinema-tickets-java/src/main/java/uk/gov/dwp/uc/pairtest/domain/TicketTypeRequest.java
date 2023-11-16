package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

/**
 * Represents a request for 1 or more tickets of a single type.
 */
public class TicketTypeRequest {

    private static final String ERROR_NEGATIVE_TICKETS = "Number of tickets cannot be negative.";

    private final int noOfTickets;
    private final Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;

        // This validation has been added to prevent the possibility of modelling a state that makes no sense.
        if (noOfTickets < 0) {
            throw new InvalidBookingRequestException(ERROR_NEGATIVE_TICKETS);
        }
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT, CHILD , INFANT
    }

}
