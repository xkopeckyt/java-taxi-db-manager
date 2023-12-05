package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

import java.util.Collection;

public class RideCategoriesCompoundMatcher extends EntityMatcher<Ride> {

    private final Collection<EntityMatcher<Ride>> rideMatchers;

    public RideCategoriesCompoundMatcher(Collection<EntityMatcher<Ride>> rideMatchers) {
        this.rideMatchers = rideMatchers;
    }

    @Override
    public boolean evaluate(Ride ride) {
        return rideMatchers.stream()
                .anyMatch(matcher -> matcher.evaluate(ride));
    }
}
