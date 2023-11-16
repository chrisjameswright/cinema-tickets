package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * Represents a validation rule that can be applied against a booking
 */
public interface ValidationRule {

    /**
     * Applies a rule to a booking request
     * @param request The booking request
     * @throws InvalidPurchaseException If the rule has failed
     */
    void apply(BookingRequest request) throws InvalidPurchaseException;

}
