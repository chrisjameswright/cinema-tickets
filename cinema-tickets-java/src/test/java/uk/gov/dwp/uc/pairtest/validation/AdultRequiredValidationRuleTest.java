package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AdultRequiredValidationRuleTest {

    private final AdultRequiredValidationRule rule = new AdultRequiredValidationRule();

    private static Stream<BookingRequest> validBookingRequests() {
        return Stream.of(
                new BookingRequest(1, 0, 0),
                new BookingRequest(1, 1, 0),
                new BookingRequest(1, 0, 1),
                new BookingRequest(2, 0, 0)
        );
    }

    @ParameterizedTest
    @DisplayName("succeeds for booking requests with one or more adults")
    @MethodSource("validBookingRequests")
    void requestsWithAdult(BookingRequest request) {
        assertDoesNotThrow(() -> {
            rule.apply(request);
        });
    }

    private static Stream<BookingRequest> invalidBookingRequests() {
        return Stream.of(
                new BookingRequest(0, 1, 0),
                new BookingRequest(0, 0, 1),
                new BookingRequest(0, 1, 1)
        );
    }

    @ParameterizedTest
    @DisplayName("fails for booking requests without at least one adult")
    @MethodSource("invalidBookingRequests")
    void requestsWithoutAdult(BookingRequest request) {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            rule.apply(request);
        });

        assertEquals("Invalid booking request: At least one adult is required per booking.", ex.getMessage());
    }

}