package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;


public class RidePassengerCuntFromMatcher extends EntityMatcher<Ride> {
    private final int countFrom;

    public RidePassengerCuntFromMatcher(int countFrom){ this.countFrom = countFrom; }

    @Override
    public boolean evaluate(Ride ride) {
        return countFrom <= ride.getPassengersCount();
    }
}
