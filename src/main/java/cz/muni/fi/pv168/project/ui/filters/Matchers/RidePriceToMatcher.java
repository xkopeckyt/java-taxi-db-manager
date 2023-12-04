package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;

import java.math.BigDecimal;

public class RidePriceToMatcher extends EntityMatcher<Ride>{
    private final BigDecimal priceTo;
    public RidePriceToMatcher(BigDecimal priceTo){ this.priceTo = priceTo; }
    public RidePriceToMatcher(float priceTo){ this.priceTo = BigDecimal.valueOf(priceTo); }
    @Override
    public boolean evaluate(Ride ride) {
        return ride.getPrice().compareTo(priceTo) <= 0;
    }
}
