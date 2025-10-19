package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

import java.math.BigDecimal;

public class RidePriceFromMatcher extends EntityMatcher<Ride>{
    private final BigDecimal priceFrom;
    public RidePriceFromMatcher(BigDecimal priceFrom){ this.priceFrom = priceFrom; }
    public RidePriceFromMatcher(Float priceFrom){
        if (priceFrom == null) {
            this.priceFrom = null;
            return;
        }
        this.priceFrom = BigDecimal.valueOf(priceFrom); }
    @Override
    public boolean evaluate(Ride ride) {
        return priceFrom == null || ride.getPrice().compareTo(priceFrom) >= 0;
    }
}
