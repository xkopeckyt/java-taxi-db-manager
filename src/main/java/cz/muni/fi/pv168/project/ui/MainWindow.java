package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Filter;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.actions.*;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.LocalDateTimeModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class MainWindow {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private final JFrame frame;
    private final Action newRideAction;
    private final Action newRideFromTemplateAction;
    private final Action showRideAction;
    private final Action editRideAction;
    private final Action deleteRideAction;
    private final Action setFilterAction;
    private final Action clearFilterAction;
    private final Action importDataAction;
    private final Action exportDataAction;
    private final Action editTechnicalLicenceAction;
    private final Action editCategoriesAction;
    private final Action aboutApplicationAction;

    private final Filter filter;


    public MainWindow() {
        frame = createFrame();
        var testDataGenerator = new TestDataGenerator();
        var categoryListModel = new CategoryListModel(testDataGenerator.getCategories());
        var ridesTable = createRidesTable(testDataGenerator.createTestRides(10), categoryListModel);
        var licence = testDataGenerator.createTestDrivingLicence();

        newRideAction = new NewRideAction(ridesTable, testDataGenerator, categoryListModel, licence);
        newRideFromTemplateAction = new NewRideFromTemplateAction(ridesTable, categoryListModel, licence, testDataGenerator);
        showRideAction = new ShowRideAction(ridesTable, testDataGenerator);
        editRideAction = new EditRideAction(ridesTable, categoryListModel, licence);
        deleteRideAction = new DeleteRideAction();
        setFilterAction = new SetFilterAction(ridesTable, testDataGenerator, categoryListModel);
        clearFilterAction = new ClearFilterAction();
        importDataAction = new ImportDataAction();
        exportDataAction = new ExportDataAction();
        editTechnicalLicenceAction = new EditTechnicalLicenceAction(licence, frame);
        editCategoriesAction = new EditCategoriesAction(categoryListModel, ridesTable);
        aboutApplicationAction = new AboutApplicationAction();
        filter = testDataGenerator.createTestFilter();
        changeActionState(0);

        frame.setJMenuBar(createMenuBar());

        var tabbedPane = new JTabbedPane();

        JPanel mainPanel = new JPanel(new GridLayout(1,1));
        ridesTable.setComponentPopupMenu(createRidesTablePopupMenu());
        mainPanel.add(new JScrollPane(ridesTable)); //, BorderLayout.CENTER

        JPanel secondaryPanel = new JPanel(new GridLayout(1,1));
        secondaryPanel.add(createStatisticsPanel());

        /*tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                }
        });*/

        tabbedPane.addTab("Rides (table)", mainPanel);
        tabbedPane.addTab("Statistics", secondaryPanel);



        JPanel toolbarPanel = new JPanel(new GridLayout(4,1));
        toolbarPanel.add(createActionToolbar());
        toolbarPanel.add(createFilter1Toolbar(testDataGenerator));
        toolbarPanel.add(createFilter2Toolbar(testDataGenerator));
        toolbarPanel.add(createFilterButtonsToolbar());
        frame.add(toolbarPanel, BorderLayout.BEFORE_FIRST_LINE);

        frame.add(tabbedPane, BorderLayout.CENTER);

        /*JPanel mainPanel = new JPanel(new GridLayout(2,1));
        frame.add(mainPanel);
        ridesTable.setComponentPopupMenu(createRidesTablePopupMenu());
        mainPanel.add(new JScrollPane(ridesTable)); //, BorderLayout.CENTER

        mainPanel.add(createStatisticsPanel());
        frame.add(mainPanel);*/


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
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        //var currencyJComboBox = new JComboBox<>(Currency.values());
        //table.setDefaultEditor(Currency.class, new DefaultCellEditor(currencyJComboBox));
        //table.setDefaultEditor(Category.class, new DefaultCellEditor(new JComboBox<>(new ComboBoxModelAdapter<>(categoryListModel))));
        return table;
    }
    private JPanel createStatisticsPanel() {
        JPanel statisticsPanel  = new JPanel(new GridLayout(1,2));
        statisticsPanel.add(new JScrollPane(createStatisticPane("Global statistics")));
        statisticsPanel.add(new JScrollPane(createStatisticPane("Filtered statistics")));
        return statisticsPanel;
    }

    private JEditorPane createStatisticPane(String name) {
        JEditorPane statsPane = new JEditorPane();
        statsPane.setContentType("text/html");
        statsPane.setEditable(false);
        StyleSheet styleSheet = ((HTMLEditorKit)statsPane.getEditorKit()).getStyleSheet();
        styleSheet.addRule("ul{margin:0px 20px;");
        statsPane.setText("<html><h3>" + name + ":</h3><ul>" +
                "<li>Rides count: 0</li><br>" +
                "<li>Total price: 0</li>" +
                "<li>Total distance: 0</li><br>" +
                "<li>Average price: 0</li>" +
                "<li>Average distance: 0</li>" +
                "</ul></html>");
        return statsPane;
    }

    private JPopupMenu createRidesTablePopupMenu() {
        var menu = new JPopupMenu();
        menu.add(showRideAction);
        menu.add(editRideAction);
        menu.add(deleteRideAction);
        return menu;
    }

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

    private JToolBar createActionToolbar() {
        var toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(newRideAction);
        toolbar.add(editRideAction);
        toolbar.add(deleteRideAction);
        toolbar.addSeparator();
        toolbar.add(importDataAction);
        toolbar.add(exportDataAction);
        toolbar.addSeparator();
        toolbar.add(editCategoriesAction);
        toolbar.addSeparator();
        toolbar.add(editTechnicalLicenceAction);
        return toolbar;
    }

    private JToolBar createFilter1Toolbar(TestDataGenerator testDataGenerator) {
        var toolbar = new JToolBar();
        toolbar.setLayout(new FlowLayout());
        toolbar.setFloatable(false);
        JTextField distanceFieldFrom = new JTextField();
        distanceFieldFrom.setPreferredSize(new Dimension(75,20));
        JTextField distanceFieldTo = new JTextField();
        distanceFieldTo.setPreferredSize(new Dimension(75,20));
        ComboBoxModel<Currency> currencyModel = new DefaultComboBoxModel<>(Currency.values());
        toolbar.add(new JLabel("Distance from:"));
        toolbar.add("Distance from:", distanceFieldFrom);
        toolbar.add(new JLabel("Distance to:"));
        toolbar.add("Distance to:", distanceFieldTo);
        toolbar.add(new JLabel("Currency:"));
        toolbar.add("Currency:", new JComboBox<>(currencyModel));

        return toolbar;
    }

    private JToolBar createFilter2Toolbar(TestDataGenerator testDataGenerator) {
        var toolbar = new JToolBar();
        toolbar.setLayout(new FlowLayout());
        toolbar.setFloatable(false);
        DateModel<LocalDateTime> dateTimeModelFrom = new LocalDateTimeModel();
        DateModel<LocalDateTime> dateTimeModelTo = new LocalDateTimeModel();
        var categoryModel = new CategoryListModel(testDataGenerator.getCategories());
        var categoryModelList = new ComboBoxModelAdapter<>(categoryModel);
        toolbar.add(new JLabel("Date from:"));
        toolbar.add("Date from:", new JDatePicker(dateTimeModelFrom)).setPreferredSize(new Dimension(100,20));
        toolbar.add(new JLabel("Date to:"));
        toolbar.add("Date to:", new JDatePicker(dateTimeModelTo)).setPreferredSize(new Dimension(100,20));
        toolbar.add(new JLabel("Category:"));
        toolbar.add("Category:", new JComboBox<>(categoryModelList)).setPreferredSize(new Dimension(50,20));

        return toolbar;
    }

    private JToolBar createFilterButtonsToolbar() {
        var toolbar = new JToolBar();
        toolbar.setLayout(new FlowLayout());
        toolbar.setFloatable(false);
        toolbar.add(new JButton("Set filter"));
        toolbar.add(new JButton("Clear filter"));

        return toolbar;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        changeActionState(selectionModel.getSelectedItemsCount());
    }

    private void changeActionState(int count) {
        showRideAction.setEnabled(count == 1);
        editRideAction.setEnabled(count == 1);
        deleteRideAction.setEnabled(count >= 1);
    }
}
