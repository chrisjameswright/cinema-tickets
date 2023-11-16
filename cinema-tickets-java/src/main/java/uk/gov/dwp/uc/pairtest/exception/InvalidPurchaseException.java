package uk.gov.dwp.uc.pairtest.exception;

/**
 * Represents all types of purchase failure that may occur
 */
public class InvalidPurchaseException extends RuntimeException {

    public InvalidPurchaseException(String message) {
        super(message);
    }

}
