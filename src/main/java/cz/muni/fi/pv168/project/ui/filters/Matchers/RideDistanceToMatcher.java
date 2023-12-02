package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;

import java.math.BigDecimal;

public class RideDistanceToMatcher extends EntityMatcher<Ride> {
    private final BigDecimal distanceTo;

    public RideDistanceToMatcher(BigDecimal distanceTo) {
        this.distanceTo = distanceTo;
    }

    public RideDistanceToMatcher(float distanceTo) {
        this.distanceTo = BigDecimal.valueOf(distanceTo);
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getDistance().compareTo(distanceTo) <= 0;
    }
}
