package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * Implements the rule that a booking must include at least 1 adult
 */
public class AdultRequiredValidationRule implements ValidationRule {
    @Override
    public void apply(BookingRequest request) throws InvalidPurchaseException {
        if (request.adults() < 1) {
            throw new InvalidBookingRequestException("At least one adult is required per booking.");
        }
    }
}
