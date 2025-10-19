package cz.muni.fi.pv168.project.ui.panels;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.function.Consumer;

public class RideTablePanel extends JPanel {
    private final JTable table;
    private final Consumer<Integer> onSelectionChange;
    private final RidesTableModel ridesTableModel;


    public RideTablePanel(RidesTableModel employeeTableModel, CategoryListModel departmentListModel, Consumer<Integer> onSelectionChange) {
        super();
        setLayout(new BorderLayout());
        table = createRidesTable(employeeTableModel, departmentListModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        this.onSelectionChange = onSelectionChange;
        this.ridesTableModel = employeeTableModel;
    }

    public JTable getTable() {
        return table;
    }

    private JTable createRidesTable(RidesTableModel model, ListModel<Category> categoryListModel) {
        var table = new JTable(model, model.getColumnModel());
        table.addComponentListener(model.getResizeListener());
        table.setRowSorter(model.getRowSorter());
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        //default editors are disabled:
        //var currencyJComboBox = new JComboBox<>(Currency.values());
        //table.setDefaultEditor(Currency.class, new DefaultCellEditor(currencyJComboBox));
        //table.setDefaultEditor(Category.class, new DefaultCellEditor(new JComboBox<>(new ComboBoxModelAdapter<>(categoryListModel))));
        return table;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();
        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }
}
