package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

import java.time.LocalDateTime;

public class RideDateFromMatcher extends EntityMatcher<Ride> {
    private final LocalDateTime dateFrom;

    public RideDateFromMatcher(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public boolean evaluate(Ride ride) {
        var dateTime = ride.getRideDateTime();
        return dateTime.isAfter(dateFrom) || dateTime.isEqual(dateFrom);
    }
}
