package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.actions.*;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private final JFrame frame;
    private final Action newRideAction;
    private final Action newRideFromTemplateAction;
    private final Action setFilterAction;
    private final Action clearFilterAction;
    private final Action importDataAction;
    private final Action exportDataAction;
    private final Action editTechnicalLicenceAction;
    private final Action editCategoriesAction;
    private final Action aboutApplicationAction;


    public MainWindow() {
        frame = createFrame();
        var testDataGenerator = new TestDataGenerator();
        var categoryListModel = new CategoryListModel(testDataGenerator.getCategories());
        var ridesTable = createRidesTable(testDataGenerator.createTestRides(10), categoryListModel);
        frame.add(new JScrollPane(ridesTable), BorderLayout.CENTER);

        newRideAction = new NewRideAction(ridesTable, testDataGenerator, categoryListModel);
        newRideFromTemplateAction = new NewRideFromTemplateAction();
        setFilterAction = new SetFilterAction();
        clearFilterAction = new ClearFilterAction();
        importDataAction = new ImportDataAction();
        exportDataAction = new ExportDataAction();
        editTechnicalLicenceAction = new EditTechnicalLicenceAction();
        editCategoriesAction = new EditCategories();
        aboutApplicationAction = new AboutApplication();
        //deleteAction.setEnabled(false);
        //frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);

        frame.setJMenuBar(createMenuBar());
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Share Car Rider");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return frame;
    }

    private JTable createRidesTable(List<Ride> rides, ListModel<Category> categoryListModel) {
        var model = new RidesTableModel(rides);
        var table = new JTable(model, model.getColumnModel());
        table.addComponentListener(model.getResizeListener());
        table.setRowSorter(model.getRowSorter());
        //table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        //var currencyJComboBox = new JComboBox<>(Currency.values());
        //table.setDefaultEditor(Currency.class, new DefaultCellEditor(currencyJComboBox));
        //table.setDefaultEditor(Category.class, new DefaultCellEditor(new JComboBox<>(new ComboBoxModelAdapter<>(categoryListModel))));
        return table;
    }

    /*
    private JPopupMenu createEmployeeTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(addAction);
        menu.add(deleteAction);
        menu.add(editAction);
        return menu;
    }
    */

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        menuBar.add(createNewRideMenu());
        menuBar.add(createFilterMenu());
        menuBar.add(createDataMenu());
        menuBar.add(createTechnicalLicenceMenu());
        menuBar.add(createCategoryMenu());
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(createAboutMenu());
        return menuBar;
    }

    private JMenu createNewRideMenu() {
        var menu = new JMenu("New Ride");
        menu.setMnemonic('n');
        menu.add(newRideAction);
        menu.add(newRideFromTemplateAction);
        return menu;
    }

    private JMenu createFilterMenu() {
        var menu = new JMenu("Filter");
        menu.setMnemonic('f');
        menu.add(setFilterAction);
        menu.add(clearFilterAction);
        return menu;
    }

    private JMenu createDataMenu() {
        var menu = new JMenu("Data");
        menu.setMnemonic('d');
        menu.add(importDataAction);
        menu.add(exportDataAction);
        return menu;
    }

    private JMenu createTechnicalLicenceMenu() {
        var menu = new JMenu("Technical license");
        menu.setMnemonic('t');
        menu.add(editTechnicalLicenceAction);
        return menu;
    }

    private JMenu createCategoryMenu() {
        var menu = new JMenu("Category");
        menu.setMnemonic('c');
        menu.add(editCategoriesAction);
        return menu;
    }

    private JMenu createAboutMenu() {
        var menu = new JMenu("About");
        menu.setMnemonic('a');
        menu.add(aboutApplicationAction);
        return menu;
    }

    /*
    private JToolBar createToolbar() {
        var toolbar = new JToolBar();
        toolbar.add(quitAction);
        toolbar.addSeparator();
        toolbar.add(addAction);
        toolbar.add(editAction);
        toolbar.add(deleteAction);
        return toolbar;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        editAction.setEnabled(selectionModel.getSelectedItemsCount() == 1);
        deleteAction.setEnabled(selectionModel.getSelectedItemsCount() >= 1);
    }
    */

}
