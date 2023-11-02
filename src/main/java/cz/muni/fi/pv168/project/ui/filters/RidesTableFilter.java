package cz.muni.fi.pv168.project.ui.filters;


import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.filters.Matchers.*;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCategoryValues;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCurrencyValues;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.util.Either;

import javax.swing.table.TableRowSorter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class holding all filters for the EmployeeTable.
 */
public final class RidesTableFilter {
    private final RidesCompoundMatcher ridesCompoundMatcher;

    public RidesTableFilter(TableRowSorter<RidesTableModel> rowSorter) {
        ridesCompoundMatcher = new RidesCompoundMatcher(rowSorter);
        rowSorter.setRowFilter(ridesCompoundMatcher);
    }

    public void filterCurrency(Either<SpecialCurrencyValues, Currency> selectedItem) {
        selectedItem.apply(
                l -> ridesCompoundMatcher.setCurrencyMatcher(l.getMatcher()),
                r -> ridesCompoundMatcher.setCurrencyMatcher(new RideCurrencyMatcher(r))
        );
    }

    public void filterCategory(List<Either<SpecialCategoryValues, Category>> selectedItems) {
        List<EntityMatcher<Ride>> matchers = new ArrayList<>();
        selectedItems.forEach(either -> either.apply(
                l -> matchers.add(l.getMatcher()),
                r -> matchers.add(new RideCategoriesMatcher(r))
        ));
        ridesCompoundMatcher.setCategoryMatcher(new RideCategoriesCompoundMatcher(matchers));
    }
    public void filterDistanceFrom(Float selectedItem) {
        ridesCompoundMatcher.setDistanceFromMatcher(new RideDistanceFromMatcher(selectedItem));
    }

    public void filterDistanceTo(Float selectedItem) {
        ridesCompoundMatcher.setDistanceToMatcher(new RideDistanceToMatcher(selectedItem));
    }

    public void filterDateFrom(LocalDateTime selectedItem) {
        ridesCompoundMatcher.setDateFromMatcher(new RideDateFromMatcher(selectedItem));
    }

    public void filterDateTo(LocalDateTime selectedItem) {
        ridesCompoundMatcher.setDateToMatcher(new RideDateToMatcher(selectedItem));
    }

    private static class RidesCompoundMatcher extends EntityMatcher<Ride> {

        private final TableRowSorter<RidesTableModel> rowSorter;
        private EntityMatcher<Ride> currencyMatcher = EntityMatchers.all();
        private EntityMatcher<Ride> categoryMatcher = EntityMatchers.all();
        private EntityMatcher<Ride> distanceFromMatcher = EntityMatchers.all();
        private EntityMatcher<Ride> distanceToMatcher = EntityMatchers.all();
        private EntityMatcher<Ride> dateFromMatcher = EntityMatchers.all();
        private EntityMatcher<Ride> dateToMatcher = EntityMatchers.all();

        private RidesCompoundMatcher(TableRowSorter<RidesTableModel> rowSorter) {
            this.rowSorter = rowSorter;
        }

        private void setCurrencyMatcher(EntityMatcher<Ride> currencyMatcher) {
            this.currencyMatcher = currencyMatcher;
            rowSorter.sort();
        }

        private void setCategoryMatcher(EntityMatcher<Ride> categoryMatcher) {
            this.categoryMatcher = categoryMatcher;
            rowSorter.sort();
        }

        public void setDistanceFromMatcher(EntityMatcher<Ride> distanceFromMatcher) {
            this.distanceFromMatcher = distanceFromMatcher;
            rowSorter.sort();
        }

        public void setDistanceToMatcher(EntityMatcher<Ride> distanceToMatcher) {
            this.distanceToMatcher = distanceToMatcher;
            rowSorter.sort();
        }

        public void setDateFromMatcher(EntityMatcher<Ride> dateFromMatcher) {
            this.dateFromMatcher = dateFromMatcher;
            rowSorter.sort();
        }

        public void setDateToMatcher(EntityMatcher<Ride> dateToMatcher) {
            this.dateToMatcher = dateToMatcher;
            rowSorter.sort();
        }

        @Override
        public boolean evaluate(Ride ride) {
            return Stream.of(currencyMatcher, categoryMatcher, distanceFromMatcher,
                            distanceToMatcher, dateFromMatcher, dateToMatcher)
                    .allMatch(m -> m.evaluate(ride));
        }
    }
}