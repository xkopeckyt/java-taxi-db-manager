package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;

public class RideDistanceToMatcher extends EntityMatcher<Ride> {
    private final Float distanceTo;

    public RideDistanceToMatcher(Float distanceTo) {
        this.distanceTo = distanceTo;
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getDistance() <= distanceTo;
    }
}
