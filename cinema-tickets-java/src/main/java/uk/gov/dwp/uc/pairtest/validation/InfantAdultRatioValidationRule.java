package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

/**
 * Implements the rule that each infant must have an adults lap to sit on
 */
public class InfantAdultRatioValidationRule implements ValidationRule {
    @Override
    public void apply(BookingRequest request) throws InvalidPurchaseException {
        if (request.adults() < request.infants()) {
            throw new InvalidBookingRequestException("At least one adult seat is required per infant.");
        }
    }
}
