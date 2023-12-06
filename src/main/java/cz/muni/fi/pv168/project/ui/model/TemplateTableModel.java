package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.sorters.StringComparator;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TemplateTableModel extends AbstractTableModel {
    private static final Map<Class<?>, Comparator<?>> COMPARATORS = Map.ofEntries(
            Map.entry(String.class, new StringComparator())
    );

    private static final List<Column<Template, ?>> COLUMNS = List.of(
            Column.readonly("Name", String.class, Template::getName)
    );

    private static final float[] columnWidthPercentage = {0.05f, 0.8f};

    private final TemplateListModel templateModel;

    public TemplateTableModel(TemplateListModel templateModel){
        this.templateModel = templateModel;
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

    public TableRowSorter<TemplateTableModel> getRowSorter() {
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

    @Override
    public int getRowCount() {
        return templateModel.getSize();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var template = getEntity(rowIndex);
        return COLUMNS.get(columnIndex).getValue(template);
    }

    public Template getEntity(int rowIndex) {
        return templateModel.getElementAt(rowIndex);
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
        var template = getEntity(rowIndex);
        COLUMNS.get(columnIndex).setValue(value, template);
    }

    public void deleteRow(int rowIndex) {
        templateModel.removeRow(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Template template) {
        int newRowIndex = templateModel.getSize();
        templateModel.addRow(template);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Template template) {
        int rowIndex = templateModel.getIndex(template);
        templateModel.updateRow(template);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}
