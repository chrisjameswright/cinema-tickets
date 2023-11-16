package uk.gov.dwp.uc.pairtest.pricing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.BookingRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceImplTest {

    private final PricingServiceImpl service = new PricingServiceImpl();

    @Test
    @DisplayName("returns correct adult price")
    void adultPrice() {
        int result = service.getPrice(TicketTypeRequest.Type.ADULT);
        assertEquals(20, result);
    }

    @Test
    @DisplayName("returns correct child price")
    void childPrice() {
        int result = service.getPrice(TicketTypeRequest.Type.CHILD);
        assertEquals(10, result);
    }

    @Test
    @DisplayName("returns correct infant price")
    void infantPrice() {
        int result = service.getPrice(TicketTypeRequest.Type.INFANT);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("returns correct total price for 1 adult")
    void oneAdultPrice() {
        BookingRequest request = new BookingRequest(1, 0, 0);
        int result = service.getTotalPrice(request);
        assertEquals(20, result);
    }

    @Test
    @DisplayName("returns correct total price for 1 child")
    void oneChildPrice() {
        BookingRequest request = new BookingRequest(0, 1, 0);
        int result = service.getTotalPrice(request);
        assertEquals(10, result);
    }

    @Test
    @DisplayName("returns correct total price for 1 infant")
    void oneInfantPrice() {
        BookingRequest request = new BookingRequest(0, 0, 1);
        int result = service.getTotalPrice(request);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("returns correct total price for 3 adults, 2 children, 1 infant")
    void multipleTicketsPrice() {
        BookingRequest request = new BookingRequest(3, 2, 1);
        int result = service.getTotalPrice(request);
        assertEquals(80, result);
    }

}