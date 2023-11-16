package uk.gov.dwp.uc.pairtest.exception;

/**
 * Represents a purchase failure due to an invalid booking request.
 */
public class InvalidBookingRequestException extends InvalidPurchaseException {

    public static final String MESSAGE = "Invalid booking request: ";

    public InvalidBookingRequestException(String reason) {
        super(MESSAGE + reason);
    }

}
