package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;

import static org.junit.jupiter.api.Assertions.*;

class TicketTypeRequestTest {

    @Test
    @DisplayName("accept positive numbers of tickets")
    void positiveTicketNumber() {
        assertDoesNotThrow(() -> {
            TicketTypeRequest result = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);

            assertEquals(result.getNoOfTickets(), 1);
            assertEquals(result.getTicketType(), TicketTypeRequest.Type.ADULT);
        });
    }

    @Test
    @DisplayName("accept zero number of tickets")
    void zeroTicketNumber() {
        assertDoesNotThrow(() -> {
            TicketTypeRequest result = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 0);

            assertEquals(result.getNoOfTickets(), 0);
            assertEquals(result.getTicketType(), TicketTypeRequest.Type.CHILD);
        });
    }

    @Test
    @DisplayName("reject negative numbers of tickets")
    void negativeTicketNumber() {
        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            new TicketTypeRequest(TicketTypeRequest.Type.INFANT, -1);
        });

        assertEquals("Invalid booking request: Number of tickets cannot be negative.", ex.getMessage());
    }

}