package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;

import java.math.BigDecimal;

public class RideDistanceFromMatcher extends EntityMatcher<Ride> {
    private final BigDecimal distanceFrom;

    public RideDistanceFromMatcher(BigDecimal distanceFrom) {
        this.distanceFrom = distanceFrom;
    }

    public RideDistanceFromMatcher(float distanceFrom) {
        this.distanceFrom = BigDecimal.valueOf(distanceFrom);
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getDistance().compareTo(distanceFrom) >= 0;
    }
}
