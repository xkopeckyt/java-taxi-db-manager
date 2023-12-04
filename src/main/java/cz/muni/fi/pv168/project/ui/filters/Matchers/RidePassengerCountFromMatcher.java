package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;


public class RidePassengerCountFromMatcher extends EntityMatcher<Ride> {
    private final int countFrom;

    public RidePassengerCountFromMatcher(int countFrom){ this.countFrom = countFrom; }

    @Override
    public boolean evaluate(Ride ride) {
        return countFrom <= ride.getPassengersCount();
    }
}
