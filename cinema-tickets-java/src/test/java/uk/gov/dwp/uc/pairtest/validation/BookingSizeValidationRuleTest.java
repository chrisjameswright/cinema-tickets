package uk.gov.dwp.uc.pairtest.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookingSizeValidationRuleTest {

    private final BookingSizeValidationRule rule = new BookingSizeValidationRule();

    private static Stream<BookingRequest> validBookingRequests() {
        return Stream.of(
                new BookingRequest(1, 0, 0),
                new BookingRequest(1, 1, 1),
                new BookingRequest(20, 0, 0),
                new BookingRequest(10, 10, 0),
                new BookingRequest(10, 5, 5)
        );
    }

    @ParameterizedTest
    @DisplayName("succeeds for booking requests with up to 20 tickets")
    @MethodSource("validBookingRequests")
    void requestsWithAdult(BookingRequest request) {
        assertDoesNotThrow(() -> {
            rule.apply(request);
        });
    }

    private static Stream<BookingRequest> invalidBookingRequests() {
        return Stream.of(
                new BookingRequest(21, 0, 0),
                new BookingRequest(20, 1, 0),
                new BookingRequest(19, 1, 1),
                new BookingRequest(11, 5, 5)
        );
    }

    @ParameterizedTest
    @DisplayName("fails for booking requests with over 20 tickets")
    @MethodSource("invalidBookingRequests")
    void requestsWithoutAdult(BookingRequest request) {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            rule.apply(request);
        });

        assertEquals("Invalid booking request: More than 20 tickets cannot be ordered at once.", ex.getMessage());
    }

}