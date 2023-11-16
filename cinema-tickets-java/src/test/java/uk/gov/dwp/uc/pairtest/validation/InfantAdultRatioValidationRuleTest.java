package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InfantAdultRatioValidationRuleTest {

    private final InfantAdultRatioValidationRule rule = new InfantAdultRatioValidationRule();

    private static Stream<BookingRequest> validBookingRequests() {
        return Stream.of(
                new BookingRequest(1, 0, 1),
                new BookingRequest(1, 5, 1),
                new BookingRequest(2, 0, 2),
                new BookingRequest(1, 0, 0),
                new BookingRequest(2, 0, 0),
                new BookingRequest(1, 1, 1),
                new BookingRequest(2, 2, 2),
                new BookingRequest(1, 3, 1)
        );
    }

    @ParameterizedTest
    @DisplayName("succeeds for booking requests with one adult per infant")
    @MethodSource("validBookingRequests")
    void requestsWithAdult(BookingRequest request) {
        assertDoesNotThrow(() -> {
            rule.apply(request);
        });
    }

    private static Stream<BookingRequest> invalidBookingRequests() {
        return Stream.of(
                new BookingRequest(0, 0, 1),
                new BookingRequest(1, 0, 2),
                new BookingRequest(0, 1, 1),
                new BookingRequest(1, 1, 2),
                new BookingRequest(1, 2, 2)
        );
    }

    @ParameterizedTest
    @DisplayName("fails for booking requests without at least one adult per infant")
    @MethodSource("invalidBookingRequests")
    void requestsWithoutAdult(BookingRequest request) {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            rule.apply(request);
        });

        assertEquals("Invalid booking request: At least one adult seat is required per infant.", ex.getMessage());
    }

}