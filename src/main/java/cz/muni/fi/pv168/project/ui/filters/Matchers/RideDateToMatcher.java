package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.model.Ride;

import java.time.LocalDateTime;

public class RideDateToMatcher extends EntityMatcher<Ride> {
    private final LocalDateTime dateTo;

    public RideDateToMatcher(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getDateTime().isBefore(dateTo.plusDays(1));
    }
}
