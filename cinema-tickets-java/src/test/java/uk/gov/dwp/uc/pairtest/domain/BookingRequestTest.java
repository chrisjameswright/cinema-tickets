package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class BookingRequestTest {

    @Test
    @DisplayName("number of adults cannot be negative")
    void negativeAdult() {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            new BookingRequest(-1, 0, 0);
        });

        assertEquals("Invalid booking request: Number of adults cannot be negative.", ex.getMessage());
    }

    @Test
    @DisplayName("number of children cannot be negative")
    void negativeChild() {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            new BookingRequest(0, -1, 0);
        });

        assertEquals("Invalid booking request: Number of children cannot be negative.", ex.getMessage());
    }

    @Test
    @DisplayName("number of infants cannot be negative")
    void negativeInfants() {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            new BookingRequest(0, 0, -1);
        });

        assertEquals("Invalid booking request: Number of infants cannot be negative.", ex.getMessage());
    }

    @Test
    @DisplayName("booking request must include at least one ticket")
    void atLeastOneTicket() {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            new BookingRequest(0, 0, 0);
        });

        assertEquals("Invalid booking request: Booking must include at least one ticket.", ex.getMessage());
    }

    @Test
    @DisplayName("convert ticket type request to booking request for 1 adult")
    void fromAdultTicketType() {
        TicketTypeRequest[] request = {
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
        };

        BookingRequest expected = new BookingRequest(1, 0, 0);
        BookingRequest actual = BookingRequest.fromTicketTypeRequests(request);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert ticket type request to booking request for 1 child")
    void fromChildTicketType() {
        TicketTypeRequest[] request = {
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)
        };

        BookingRequest expected = new BookingRequest(0, 1, 0);
        BookingRequest actual = BookingRequest.fromTicketTypeRequests(request);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert ticket type request to booking request for 1 infant")
    void fromInfantTicketType() {
        TicketTypeRequest[] request = {
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
        };

        BookingRequest expected = new BookingRequest(0, 0, 1);
        BookingRequest actual = BookingRequest.fromTicketTypeRequests(request);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert ticket type request to booking request for 3 adult, 2 children, 1 infant with one entry per type")
    void fromMixedTicketType() {
        TicketTypeRequest[] request = {
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
        };

        BookingRequest expected = new BookingRequest(3, 2, 1);
        BookingRequest actual = BookingRequest.fromTicketTypeRequests(request);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert ticket type request to booking request for 4 adult, 4 children, 4 infants with multiple entries per type")
    void fromMixedRepeatedTicketType() {
        TicketTypeRequest[] request = {
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
        };

        BookingRequest expected = new BookingRequest(4, 4, 4);
        BookingRequest actual = BookingRequest.fromTicketTypeRequests(request);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("convert ticket type request to booking request for 4 adult, 4 children, 4 infants with multiple entries per type")
    void fromMixedRepeatedTicketType2() {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            BookingRequest.fromTicketTypeRequests(null);
        });

        assertEquals("Invalid booking request: TicketTypeRequest cannot be null.", ex.getMessage());
    }
}