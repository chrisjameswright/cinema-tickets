package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceImplTest {

    private final ValidationServiceImpl validator = new ValidationServiceImpl();

    @Test
    @DisplayName("succeeds for booking that meets all validation rules")
    void validBooking() {
        BookingRequest request = new BookingRequest(1, 1, 1);

        assertDoesNotThrow(() -> {
            validator.applyRules(request);
        });
    }

    @Test
    @DisplayName("fails for booking with over 20 tickets")
    void invalidBookingSize() {
        BookingRequest request = new BookingRequest(21, 0, 0);

        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            validator.applyRules(request);
        });

        assertEquals("Invalid booking request: More than 20 tickets cannot be ordered at once.", ex.getMessage());
    }

    @Test
    @DisplayName("fails for booking with no adults")
    void invalidAdultNumber() {
        BookingRequest request = new BookingRequest(0, 1, 0);

        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            validator.applyRules(request);
        });

        assertEquals("Invalid booking request: At least one adult is required per booking.", ex.getMessage());
    }

    @Test
    @DisplayName("fails for booking with more infants than adults")
    void invalidInfantAdultRatio() {
        BookingRequest request = new BookingRequest(1, 0, 2);

        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            validator.applyRules(request);
        });

        assertEquals("Invalid booking request: At least one adult seat is required per infant.", ex.getMessage());
    }

    @Test
    @DisplayName("fails for null booking request")
    void nullBookingRequest() {
        InvalidPurchaseException ex = assertThrowsExactly(InvalidPurchaseException.class, () -> {
            validator.applyRules(null);
        });

        assertEquals("Booking request cannot be null.", ex.getMessage());
    }

}