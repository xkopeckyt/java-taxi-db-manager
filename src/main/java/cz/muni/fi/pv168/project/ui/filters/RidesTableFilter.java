package cz.muni.fi.pv168.project.ui.filters;


import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.filters.Matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.Matchers.EntityMatchers;
import cz.muni.fi.pv168.project.ui.filters.Matchers.RideCategoriesMatcher;
import cz.muni.fi.pv168.project.ui.filters.Matchers.RideCurrencyMatcher;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCategoryValues;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCurrencyValues;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.util.Either;

import javax.swing.table.TableRowSorter;
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

    public void filterCategory(Either<SpecialCategoryValues, Category> selectedItem) {
        selectedItem.apply(
                l -> ridesCompoundMatcher.setCategoryMatcher(l.getMatcher()),
                r -> ridesCompoundMatcher.setCategoryMatcher(new RideCategoriesMatcher(r))
        );
    }

    private static class RidesCompoundMatcher extends EntityMatcher<Ride> {

        private final TableRowSorter<RidesTableModel> rowSorter;
        private EntityMatcher<Ride> currencyMatcher = EntityMatchers.all();
        private EntityMatcher<Ride> categoryMatcher = EntityMatchers.all();

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

        @Override
        public boolean evaluate(Ride ride) {
            return Stream.of(currencyMatcher, categoryMatcher)
                    .allMatch(m -> m.evaluate(ride));
        }
    }
}