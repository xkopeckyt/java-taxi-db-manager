package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.renderers.DateTimeCellRenderer;
import cz.muni.fi.pv168.project.ui.renderers.FloatCellRenderer;
import cz.muni.fi.pv168.project.ui.sorters.EnumComparator;
import cz.muni.fi.pv168.project.ui.sorters.FloatComparator;
import cz.muni.fi.pv168.project.ui.sorters.IntCompatator;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.*;

public class RidesTableModel extends AbstractTableModel implements EntityTableModel<Ride>{
    private static final Map<Class<?>, Comparator<?>> COMPARATORS = Map.ofEntries(
            Map.entry(int.class, new IntCompatator()),
            Map.entry(Enum.class, new EnumComparator())
    );

    private static final List<Column<Ride, ?>> COLUMNS = List.of(
            Column.readonly("Date & Time", LocalDateTime.class, Ride::getRideDateTime, new DateTimeCellRenderer()),
            Column.readonly("Price", BigDecimal.class, Ride::getPrice, new FloatCellRenderer(2)),
            Column.readonly("Distance", BigDecimal.class, Ride::getDistance, new FloatCellRenderer(2)),
            Column.readonly("Currency", Currency.class, Ride::getOriginalCurrency),
            Column.readonly("Passengers", int.class, Ride::getPassengersCount),
            Column.readonly("Category", Category.class, Ride::getCategory)
    );
    private static final float[] columnWidthPercentage = {0.28f, 0.1f, 0.1f, 0.13f, 0.14f, 0.25f};

    private final List<Ride> rides;

    public RidesTableModel(List<Ride> rides) {
        this.rides = new ArrayList<>(rides);
    }

    public TableColumnModel getColumnModel() {
        var tableColumnModel = new DefaultTableColumnModel();
        for (int i = 0; i < COLUMNS.size(); i++) {
            var column = COLUMNS.get(i);
            var tableColumn = new TableColumn(i);
            tableColumn.setHeaderValue(column.getName());
            var cellRenderer = column.getCellRenderer();
            if (cellRenderer != null)
                tableColumn.setCellRenderer(cellRenderer);
            tableColumnModel.addColumn(tableColumn);
        }
        return tableColumnModel;
    }

    public TableRowSorter<RidesTableModel> getRowSorter() {
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
        if (!(e.getSource() instanceof JTable table)) {
            throw new RuntimeException("nope");
        }
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
        return rides.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var employee = getEntity(rowIndex);
        return COLUMNS.get(columnIndex).getValue(employee);
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
        var employee = getEntity(rowIndex);
        COLUMNS.get(columnIndex).setValue(value, employee);
    }

    public void deleteRow(int rowIndex) {
        rides.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Ride ride) {
        int newRowIndex = rides.size();
        rides.add(ride);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Ride ride) {
        int rowIndex = rides.indexOf(ride);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    public BigDecimal totalPrice(List<Ride> rides){
        BigDecimal count = BigDecimal.valueOf(0);
        for(Ride ride : rides){
            count = count.add(ride.getPrice());
        }
        return count;
    }
    public BigDecimal totalDistance(List<Ride> rides){
        BigDecimal distance = BigDecimal.valueOf(0);
        for(Ride ride : rides){
            distance = distance.add(ride.getDistance());
        }
        return distance;
    }
    public BigDecimal averageDistance(List<Ride> rides){
        if(rides.isEmpty()){
            return BigDecimal.valueOf(0);
        }
        return totalDistance(rides).divide(BigDecimal.valueOf(rides.size()), new MathContext(2));
    }
    public BigDecimal averagePrice(List<Ride> rides){
        if(rides.isEmpty()){
            return BigDecimal.valueOf(0);
        }
        return totalPrice(rides).divide(BigDecimal.valueOf(rides.size()), new MathContext(2));
    }

    public void deleteAll() {
        int lastIndex = rides.size() - 1;
        rides.clear();
        if (lastIndex >= 0) {
            fireTableRowsDeleted(0, lastIndex);
        }
    }

    @Override
    public Ride getEntity(int rowIndex) {
        return rides.get(rowIndex);
    }

    public List<Ride> getAllRides() {
        return new ArrayList<Ride>(rides);
    }
}
