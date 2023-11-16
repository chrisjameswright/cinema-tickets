package uk.gov.dwp.uc.pairtest.pricing;

import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

/**
 * Calculates pricing for a ticket or booking. This allows different pricing implementations to be provided, or for
 * future extensibility to provide more complex pricing rules (such as "family tickets" or discounts).
 */
public interface PricingService {

    /**
     * Gets the price for a single type of ticket
     * @param type The type of ticket
     * @return The price
     */
    int getPrice(TicketTypeRequest.Type type);

    /**
     * Gets the total price for an entire booking
     * @param request The overall request
     * @return The total price for all tickets in the booking
     */
    default int getTotalPrice(BookingRequest request) {
        return (request.adults() * getPrice(TicketTypeRequest.Type.ADULT)) +
                (request.children() * getPrice(TicketTypeRequest.Type.CHILD)) +
                (request.infants() * getPrice(TicketTypeRequest.Type.INFANT));
    }

}
