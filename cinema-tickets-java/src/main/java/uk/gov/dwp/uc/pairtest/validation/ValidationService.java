package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

/**
 * Provides an interface for applying one or more validation rules to an overall booking. This allows different sets of
 * rules to be applied if needed in future iterations.
 */
public interface ValidationService {

    /**
     * Apply one or more validation rules to the booking request
     * @param request The overall booking
     * @throws InvalidPurchaseException If any rules have failed
     */
    void applyRules(BookingRequest request) throws InvalidPurchaseException;

}
