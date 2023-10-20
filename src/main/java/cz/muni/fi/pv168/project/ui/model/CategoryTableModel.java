package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.sorters.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CategoryTableModel extends AbstractTableModel {
    private CategoryListModel categoryModel;
    private static final Map<Class<?>, Comparator<?>> COMPARATORS = Map.ofEntries(
            Map.entry(long.class, new LongComparator()),
            Map.entry(String.class, new StringComparator())
    );
    private static final List<Column<Category, ?>> COLUMNS = List.of(
            Column.readonly("ID", Long.class, Category::getId),
            Column.readonly("Name", String.class, Category::getName)
    );
    public CategoryTableModel(CategoryListModel categoryModel){
        this.categoryModel = categoryModel;
    }
    public TableColumnModel getColumnModel() {
        var tableColumnModel = new DefaultTableColumnModel();
        for (int i = 0; i < COLUMNS.size(); i++) {
            var column = COLUMNS.get(i);
            var tableColumn = new TableColumn(i);
            tableColumn.setHeaderValue(column.getName());
            DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
            leftRenderer.setHorizontalAlignment(JLabel.CENTER);
            tableColumn.setCellRenderer(leftRenderer);
            tableColumnModel.addColumn(tableColumn);
        }
        return tableColumnModel;
    }

    public TableRowSorter<CategoryTableModel> getRowSorter() {
        var rowSorter = new TableRowSorter<>(this);
        for (int i = 0; i < COLUMNS.size(); i++) {
            var classType = COLUMNS.get(i).getColumnType();
            var searchClass = classType.isEnum() ? Enum.class : classType;
            var comparator = COMPARATORS.getOrDefault(searchClass, null);
            if (comparator != null) {
                rowSorter.setComparator(i, comparator);
            }
        }
        return rowSorter;
    }

    public ComponentAdapter getResizeListener() {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeColumns(e);
            }
        };
    }

    private void resizeColumns(ComponentEvent e) {
        if (!(e.getSource() instanceof JTable table)) throw new RuntimeException("nope");
        TableColumn column;
        TableColumnModel jTableColumnModel = table.getColumnModel();
        int tW = jTableColumnModel.getTotalColumnWidth();
        int cantCols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++) {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(columnWidthPercentage[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }
    float[] columnWidthPercentage = {0.05f, 0.8f};
    @Override
    public int getRowCount() {
        return categoryModel.getSize();
    }
    @Override
    public int getColumnCount() {
        return COLUMNS.size();  // ID and Name
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var category = getEntity(rowIndex);
        switch(columnIndex){
            case 0:
                return category.getId();
            case 1:
                return category.getName();
            default:
                throw new IllegalArgumentException("Invalid column index: " + columnIndex);
        }
    }
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMNS.get(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMNS.get(columnIndex).getColumnType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return COLUMNS.get(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        var category = getEntity(rowIndex);
        COLUMNS.get(columnIndex).setValue(value, category);
    }
    public Category getEntity(int rowIndex) {
        return categoryModel.getElementAt(rowIndex);
    }

    public void deleteRow(int rowIndex) {
        categoryModel.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Category category) {
        int newRowIndex = categoryModel.getSize();
        categoryModel.add(category);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Category category) {
        int rowIndex = categoryModel.getIndex(category);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}

