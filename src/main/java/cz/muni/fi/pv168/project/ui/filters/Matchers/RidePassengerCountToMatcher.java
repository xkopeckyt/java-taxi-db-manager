package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

public class RidePassengerCountToMatcher extends EntityMatcher<Ride>{
    private final int countTo;

    public RidePassengerCountToMatcher(int countTo){ this.countTo = countTo; }

    @Override
    public boolean evaluate(Ride ride) {
        return countTo >= ride.getPassengersCount();
    }
}
