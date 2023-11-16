package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

/**
 * Implements the rule that bookings cannot include more than 20 tickets
 */
public class BookingSizeValidationRule implements ValidationRule {
    @Override
    public void apply(BookingRequest request) throws InvalidPurchaseException {
        if (request.adults() + request.children() + request.infants() > 20) {
            throw new InvalidBookingRequestException("More than 20 tickets cannot be ordered at once.");
        }
    }
}
