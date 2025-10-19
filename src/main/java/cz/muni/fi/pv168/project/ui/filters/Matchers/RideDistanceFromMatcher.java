package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

import java.math.BigDecimal;

public class RideDistanceFromMatcher extends EntityMatcher<Ride> {
    private final BigDecimal distanceFrom;

    public RideDistanceFromMatcher(BigDecimal distanceFrom) {
        this.distanceFrom = distanceFrom;
    }

    public RideDistanceFromMatcher(Float distanceFrom) {
        if (distanceFrom == null) {
            this.distanceFrom = null;
            return;
        }
        this.distanceFrom = BigDecimal.valueOf(distanceFrom);
    }

    @Override
    public boolean evaluate(Ride ride) {
        return distanceFrom == null || ride.getDistance().compareTo(distanceFrom) >= 0;
    }
}
