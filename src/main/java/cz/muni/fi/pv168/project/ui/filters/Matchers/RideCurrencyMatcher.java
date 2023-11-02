package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;

import java.util.Collection;
import java.util.List;

public class RideCurrencyMatcher extends EntityMatcher<Ride> {
    private final Currency currency;

    public RideCurrencyMatcher(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getOriginalCurrency() == currency;
    }
}

