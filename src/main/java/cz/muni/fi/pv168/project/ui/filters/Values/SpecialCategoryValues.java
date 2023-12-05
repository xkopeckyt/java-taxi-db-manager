package cz.muni.fi.pv168.project.ui.filters.Values;

import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.ui.filters.Matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.Matchers.EntityMatchers;

import java.util.Objects;

public enum SpecialCategoryValues {
    ALL(EntityMatchers.all());

    private final EntityMatcher<Ride> matcher;

    SpecialCategoryValues(EntityMatcher<Ride> matcher) {
        this.matcher = Objects.requireNonNull(matcher, "matcher cannot be null");
    }

    public EntityMatcher<Ride> getMatcher() {
        return matcher;
    }
}
