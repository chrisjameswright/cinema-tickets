package uk.gov.dwp.uc.pairtest.pricing;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class PricingServiceImpl implements PricingService {

    private static final int ADULT_PRICE = 20;
    private static final int CHILD_PRICE = 10;
    private static final int INFANT_PRICE = 0;

    @Override
    public int getPrice(TicketTypeRequest.Type type) {
        return switch (type) {
            case ADULT -> ADULT_PRICE;
            case CHILD -> CHILD_PRICE;
            case INFANT -> INFANT_PRICE;
        };
    }
}
