package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

import java.math.BigDecimal;

public class RideDistanceToMatcher extends EntityMatcher<Ride> {
    private final BigDecimal distanceTo;

    public RideDistanceToMatcher(BigDecimal distanceTo) {
        this.distanceTo = distanceTo;
    }

    public RideDistanceToMatcher(Float distanceTo) {
        if (distanceTo == null) {
            this.distanceTo = null;
            return;
        }
        this.distanceTo = BigDecimal.valueOf(distanceTo);
    }

    @Override
    public boolean evaluate(Ride ride) {
        return distanceTo == null || ride.getDistance().compareTo(distanceTo) <= 0;
    }
}
