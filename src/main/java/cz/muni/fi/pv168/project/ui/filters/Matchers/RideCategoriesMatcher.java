package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Ride;

public class RideCategoriesMatcher extends EntityMatcher<Ride> {

    private final Category category;

    public RideCategoriesMatcher(Category category) {
        this.category = category;
    }

    @Override
    public boolean evaluate(Ride ride) {
        return ride.getCategory().equals(category);
    }
}
