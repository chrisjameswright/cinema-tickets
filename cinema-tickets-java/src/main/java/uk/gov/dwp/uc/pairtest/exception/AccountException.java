package uk.gov.dwp.uc.pairtest.exception;

/**
 * Represents a purchase failure caused by an invalid account
 */
public class AccountException extends InvalidPurchaseException {

    public static final String MESSAGE = "Invalid account number.";

    public AccountException() {
        super(MESSAGE);
    }

}
