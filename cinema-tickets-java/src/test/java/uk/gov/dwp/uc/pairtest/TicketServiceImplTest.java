package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.account.AccountService;
import uk.gov.dwp.uc.pairtest.account.AccountServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.AccountException;
import uk.gov.dwp.uc.pairtest.exception.InvalidBookingRequestException;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.pricing.PricingService;
import uk.gov.dwp.uc.pairtest.pricing.PricingServiceImpl;
import uk.gov.dwp.uc.pairtest.validation.ValidationService;
import uk.gov.dwp.uc.pairtest.validation.ValidationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    PricingService pricingService = new PricingServiceImpl();
    AccountService accountService = new AccountServiceImpl();
    ValidationService validationService = new ValidationServiceImpl();

    TicketPaymentService ticketPaymentService = Mockito.mock(TicketPaymentService.class);
    SeatReservationService seatReservationService = Mockito.mock(SeatReservationService.class);

    private final TicketServiceImpl service = new TicketServiceImpl(
            pricingService,
            accountService,
            validationService,
            ticketPaymentService,
            seatReservationService
    );

    @BeforeEach
    void resetMocks() {
        Mockito.reset(ticketPaymentService);
        Mockito.reset(seatReservationService);
    }

    @Test
    @DisplayName("fail if accountNumber is null")
    void accountNumberNull() {
        TicketTypeRequest[] validRequests = { new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1) };

        InvalidPurchaseException ex = assertThrowsExactly(InvalidPurchaseException.class, () -> {
            service.purchaseTickets(null, validRequests);
        });

        assertEquals("accountId cannot be null.", ex.getMessage());
        Mockito.verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("fail if ticketTypeRequests is null")
    void ticketTypeRequestsNull() {
        Long validAccountId = 1L;

        InvalidPurchaseException ex = assertThrowsExactly(InvalidPurchaseException.class, () -> {
            service.purchaseTickets(validAccountId, null);
        });

        assertEquals("ticketTypeRequests cannot be null.", ex.getMessage());
        Mockito.verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("fail if accountId is invalid")
    void invalidAccountId() {
        Long invalidAccountId = 0L;
        TicketTypeRequest[] validRequests = { new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1) };

        AccountException ex = assertThrowsExactly(AccountException.class, () -> {
            service.purchaseTickets(invalidAccountId, validRequests);
        });

        assertEquals("Invalid account number.", ex.getMessage());
        Mockito.verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    @Test
    @DisplayName("fail if validation is not met")
    void invalidTicketCombination() {
        Long validAccountId = 1L;
        TicketTypeRequest[] validRequests = { new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1) };

        InvalidBookingRequestException ex = assertThrowsExactly(InvalidBookingRequestException.class, () -> {
            service.purchaseTickets(validAccountId, validRequests);
        });

        assertEquals("Invalid booking request: At least one adult is required per booking.", ex.getMessage());
        Mockito.verifyNoInteractions(ticketPaymentService, seatReservationService);
    }

    private static Stream<Arguments> validTicketTypeRequests() {
        return Stream.of(
                Arguments.of(new TicketTypeRequest[]{
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1)
                }, 20, 1),
                Arguments.of(new TicketTypeRequest[]{
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2)
                }, 40, 2),
                Arguments.of(new TicketTypeRequest[]{
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2)
                }, 40, 3),
                Arguments.of(new TicketTypeRequest[]{
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2)
                }, 40, 3),
                Arguments.of(new TicketTypeRequest[]{
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
                }, 80, 5),
                Arguments.of(new TicketTypeRequest[]{
                        new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1),
                        new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
                        new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3)
                }, 80, 5)
        );
    }

    @ParameterizedTest
    @DisplayName("succeeds for valid ticket type requests")
    @MethodSource("validTicketTypeRequests")
    void requestsWithAdult(TicketTypeRequest[] requests, int expectedPrice, int expectedSeats) {
        Long validAccountId = 1L;

        service.purchaseTickets(validAccountId, requests);

        Mockito.verify(ticketPaymentService).makePayment(validAccountId, expectedPrice);
        Mockito.verify(seatReservationService).reserveSeat(validAccountId, expectedSeats);
    }
}