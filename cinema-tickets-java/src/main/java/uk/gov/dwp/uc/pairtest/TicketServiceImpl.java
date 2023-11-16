package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.*;
import thirdparty.seatbooking.*;

import uk.gov.dwp.uc.pairtest.account.*;
import uk.gov.dwp.uc.pairtest.pricing.*;
import uk.gov.dwp.uc.pairtest.domain.*;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.*;

public class TicketServiceImpl implements TicketService {

    private final PricingService pricingService;
    private final AccountService accountService;
    private final ValidationService validationService;
    private final TicketPaymentService paymentService;
    private final SeatReservationService seatReservationService;

    // In a full implementation this should be configured with dependency injection with singleton instances where relevant
    public TicketServiceImpl(PricingService pricingService,
                             AccountService accountService,
                             ValidationService validationService,
                             TicketPaymentService paymentService,
                             SeatReservationService seatReservationService) {
        this.pricingService = pricingService;
        this.accountService = accountService;
        this.validationService = validationService;
        this.paymentService = paymentService;
        this.seatReservationService = seatReservationService;
    }

    // This implementation uses exception flow control to align with the provided interface. However, it would be
    // preferable to model return types more explicitly, e.g., using something like "Try" from vavr or an "Either" class
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        checkNotNull(accountId, "accountId");
        checkNotNull(ticketTypeRequests, "ticketTypeRequests");

        BookingRequest combinedRequest = BookingRequest.fromTicketTypeRequests(ticketTypeRequests);

        accountService.validate(accountId);
        validationService.applyRules(combinedRequest);
        paymentService.makePayment(accountId, pricingService.getTotalPrice(combinedRequest));
        seatReservationService.reserveSeat(accountId, combinedRequest.adults() + combinedRequest.children());
    }

    private void checkNotNull (Object param, String name) {
        if (param == null) {
            throw new InvalidPurchaseException(name + " cannot be null.");
        }
    }

}
