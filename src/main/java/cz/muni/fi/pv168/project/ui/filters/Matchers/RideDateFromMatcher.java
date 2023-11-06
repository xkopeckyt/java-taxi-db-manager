package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;

import java.time.LocalDateTime;

public class RideDateFromMatcher extends EntityMatcher<Ride> {
    private final LocalDateTime dateFrom;

    public RideDateFromMatcher(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getDateTime().isAfter(dateFrom); //dateFrom.minusDays(1)
    }
}
