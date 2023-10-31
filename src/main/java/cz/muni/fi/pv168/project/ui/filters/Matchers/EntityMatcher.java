package cz.muni.fi.pv168.project.ui.filters.Matchers;

import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.RowFilter;

public abstract class EntityMatcher<T> extends RowFilter<EntityTableModel<T>, Integer> {

    @Override
    public boolean include(RowFilter.Entry<? extends EntityTableModel<T>, ? extends Integer> entry) {
        EntityTableModel<T> tableModel = entry.getModel();
        int rowIndex = entry.getIdentifier();
        T entity = tableModel.getEntity(rowIndex);

        return evaluate(entity);
    }

    public abstract boolean evaluate(T entity);
}
