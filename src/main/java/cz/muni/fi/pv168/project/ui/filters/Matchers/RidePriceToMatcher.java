package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

import java.math.BigDecimal;

public class RidePriceToMatcher extends EntityMatcher<Ride>{
    private final BigDecimal priceTo;
    public RidePriceToMatcher(BigDecimal priceTo){ this.priceTo = priceTo; }
    public RidePriceToMatcher(Float priceTo){
        if (priceTo == null) {
            this.priceTo = null;
            return;
        }
        this.priceTo = BigDecimal.valueOf(priceTo); }
    @Override
    public boolean evaluate(Ride ride) {
        return priceTo == null || ride.getPrice().compareTo(priceTo) <= 0;
    }
}
