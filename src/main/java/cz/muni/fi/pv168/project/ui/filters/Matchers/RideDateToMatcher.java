package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Ride;

import java.time.LocalDateTime;

public class RideDateToMatcher extends EntityMatcher<Ride> {
    private final LocalDateTime dateTo;

    public RideDateToMatcher(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean evaluate(Ride ride) {
        var dateTime = ride.getRideDateTime();
        return dateTime.isBefore(dateTo) || dateTime.isEqual(dateTo);
    }
}
