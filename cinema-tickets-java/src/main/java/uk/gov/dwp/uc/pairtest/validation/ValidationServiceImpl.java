package uk.gov.dwp.uc.pairtest.validation;

import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

public class ValidationServiceImpl implements ValidationService {

    // In a full implementation, this list could be controlled by or derived from configuration
    private final List<ValidationRule> rules = List.of(
            new BookingSizeValidationRule(),
            new AdultRequiredValidationRule(),
            new InfantAdultRatioValidationRule()
    );

    @Override
    public void applyRules(BookingRequest request) throws InvalidPurchaseException {
        if (request == null) {
            throw new InvalidPurchaseException("Booking request cannot be null.");
        }
        rules.forEach(r -> r.apply(request));
    }
}
