package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;

public class RideDistanceFromMatcher extends EntityMatcher<Ride> {
    private final Float distanceFrom;

    public RideDistanceFromMatcher(Float distanceFrom) {
        this.distanceFrom = distanceFrom;
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getDistance() >= distanceFrom;
    }
}
