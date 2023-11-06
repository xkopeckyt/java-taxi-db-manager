package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.actions.*;
import cz.muni.fi.pv168.project.ui.filters.RidesTableFilter;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCategoryValues;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCurrencyValues;
import cz.muni.fi.pv168.project.ui.filters.components.FilterComboboxBuilder;
import cz.muni.fi.pv168.project.ui.filters.components.FilterListModelBuilder;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.LocalDateTimeModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterCategoryValuesRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterCurrencyValuesRenderer;
import cz.muni.fi.pv168.project.util.Either;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainWindow {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private final JFrame frame;
    private final Action newRideAction;
    private final Action newRideFromTemplateAction;
    private final Action showRideAction;
    private final Action editRideAction;
    private final Action deleteRideAction;
    private final Action importDataAction;
    private final Action exportDataAction;
    private final Action editTechnicalLicenceAction;
    private final Action editCategoriesAction;
    private final Action aboutApplicationAction;

    public MainWindow() {
        frame = createFrame();
        var testDataGenerator = new TestDataGenerator();
        var categoryListModel = new CategoryListModel(testDataGenerator.getCategories());
        var ridesTableModel = new RidesTableModel(testDataGenerator.createTestRides(10));
        var ridesTable = createRidesTable(ridesTableModel, categoryListModel);
        var licence = testDataGenerator.createTestDrivingLicence();

        newRideAction = new NewRideAction(ridesTable, testDataGenerator, categoryListModel, licence);
        newRideFromTemplateAction = new NewRideFromTemplateAction(ridesTable, categoryListModel, licence, testDataGenerator);
        showRideAction = new ShowRideAction(ridesTable, testDataGenerator);
        editRideAction = new EditRideAction(ridesTable, categoryListModel, licence);
        deleteRideAction = new DeleteRideAction(ridesTable);
        importDataAction = new ImportDataAction();
        exportDataAction = new ExportDataAction();
        editTechnicalLicenceAction = new EditTechnicalLicenceAction(licence, frame);
        editCategoriesAction = new EditCategoriesAction(categoryListModel, ridesTable);
        aboutApplicationAction = new AboutApplicationAction();
        changeActionState(0);

        frame.setJMenuBar(createMenuBar());

        var tabbedPane = new JTabbedPane();

        JPanel mainPanel = new JPanel(new GridLayout(1,1));
        ridesTable.setComponentPopupMenu(createRidesTablePopupMenu());
        mainPanel.add(new JScrollPane(ridesTable)); //, BorderLayout.CENTER

        JPanel secondaryPanel = new JPanel(new GridLayout(1,1));
        JPanel statisticsPanel = createStatisticsPanel(ridesTable);
        secondaryPanel.add(statisticsPanel);
        ridesTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateStatisticsPanel(statisticsPanel, ridesTable, true);
                    }
                });
            }
        });


        tabbedPane.addTab("Rides (table)", mainPanel);
        tabbedPane.addTab("Statistics", secondaryPanel);

        frame.add(createToolBar(ridesTable, categoryListModel, statisticsPanel), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(tabbedPane, BorderLayout.CENTER);

        /*JPanel mainPanel = new JPanel(new GridLayout(2,1));
        frame.add(mainPanel);
        ridesTable.setComponentPopupMenu(createRidesTablePopupMenu());
        mainPanel.add(new JScrollPane(ridesTable)); //, BorderLayout.CENTER

        mainPanel.add(createStatisticsPanel());
        frame.add(mainPanel);*/


        frame.pack();
    }

    private void setActionListeners(RidesTableFilter ridesTableFilter, JTextField distanceFrom, JTextField distanceTo,
                                    LocalDateTimeModel dateFrom, LocalDateTimeModel dateTo,
                                    JTable ridesTable, JPanel statisticsPanel) {
        distanceFrom.addActionListener(e -> {
            var distanceFromText = distanceFrom.getText();
            if (distanceFromText.length() != 0) {
                ridesTableFilter.filterDistanceFrom(Float.parseFloat(distanceFromText));
            } else {
                ridesTableFilter.filterDistanceFrom(Float.MIN_VALUE);
            }
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        distanceTo.addActionListener(e -> {
            var distanceToText = distanceTo.getText();
            if (distanceToText.length() != 0) {
                ridesTableFilter.filterDistanceTo(Float.parseFloat(distanceToText));
            } else {
                ridesTableFilter.filterDistanceTo(Float.MAX_VALUE);
            }
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        dateFrom.addChangeListener(e -> {
            if (dateFrom.isSelected()) {
                ridesTableFilter.filterDateFrom(dateFrom.getValue());
            } else {
                ridesTableFilter.filterDateFrom(LocalDateTime.MIN.plusDays(1));
            }
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        dateTo.addChangeListener(e -> {
            if (dateTo.isSelected()) {
                ridesTableFilter.filterDateTo(dateTo.getValue());
            } else {
                ridesTableFilter.filterDateTo(LocalDateTime.MAX.minusDays(1));
            }
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });
    }

    public void resetFilters (JTextField distanceFrom, JTextField distanceTo, JComboBox currency,
                              LocalDateTimeModel dateFrom, LocalDateTimeModel dateTo, JList category) {
        distanceFrom.setText(null);
        distanceFrom.postActionEvent();
        distanceTo.setText(null);
        distanceTo.postActionEvent();
        currency.setSelectedIndex(0);
        category.setSelectedIndex(0);
        dateFrom.setValue(null);
        dateTo.setValue(null);
    }
    private static JComboBox<Either<SpecialCurrencyValues, Currency>> createCurrencyFilter(
            RidesTableFilter ridesTableFilter, JTable ridesTable, JPanel statisticsPanel) {
        return FilterComboboxBuilder.create(SpecialCurrencyValues.class, Currency.values())
                .setSelectedItem(SpecialCurrencyValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterCurrencyValuesRenderer())
                .setValuesRenderer(new CurrencyRenderer())
                .setFilter(item -> {
                    ridesTableFilter.filterCurrency(item);
                    updateStatisticsPanel(statisticsPanel, ridesTable, false);
                })
                .build();
    }

    private static JList<Either<SpecialCategoryValues, Category>> createCategoryFilter(
            RidesTableFilter ridesTableFilter, CategoryListModel categoryListModel,
            JTable ridesTable, JPanel statisticsPanel) {
        return FilterListModelBuilder.create(SpecialCategoryValues.class, categoryListModel)
                .setSelectedIndex(0)
                .setVisibleRowsCount(5)
                .setSpecialValuesRenderer(new SpecialFilterCategoryValuesRenderer())
                .setValuesRenderer(new CategoryRenderer())
                .setFilter(item -> {
                    ridesTableFilter.filterCategory(item);
                    updateStatisticsPanel(statisticsPanel, ridesTable, false);
                })
                .build();
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

    private JTable createRidesTable(RidesTableModel model, ListModel<Category> categoryListModel) {
        var table = new JTable(model, model.getColumnModel());
        table.addComponentListener(model.getResizeListener());
        table.setRowSorter(model.getRowSorter());
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        //var currencyJComboBox = new JComboBox<>(Currency.values());
        //table.setDefaultEditor(Currency.class, new DefaultCellEditor(currencyJComboBox));
        //table.setDefaultEditor(Category.class, new DefaultCellEditor(new JComboBox<>(new ComboBoxModelAdapter<>(categoryListModel))));
        return table;
    }
    private JPanel createStatisticsPanel(JTable ridesTable) {
        JPanel statisticsPanel  = new JPanel(new GridLayout(1,2));
        statisticsPanel.add(new JScrollPane(createStatisticPane("Global statistics", false, ridesTable)));
        statisticsPanel.add(new JScrollPane(createStatisticPane("Filtered statistics", true, ridesTable)));
        return statisticsPanel;
    }

    private JEditorPane createStatisticPane(String name, boolean filtered, JTable ridesTable) {
        JEditorPane statsPane = new JEditorPane();
        statsPane.setContentType("text/html");
        statsPane.setEditable(false);
        StyleSheet styleSheet = ((HTMLEditorKit)statsPane.getEditorKit()).getStyleSheet();
        styleSheet.addRule("ul{margin:0px 20px;");
        setStatisticPane(statsPane, name, filtered, ridesTable);
        return statsPane;
    }
    public static void updateStatisticsPanel(JPanel statisticsPanel, JTable ridesTable, boolean both) {
        Component[] components = statisticsPanel.getComponents();
        if (components.length >= 2) {
            if(both) {
                JScrollPane globalScrollPane = (JScrollPane) components[0];
                JEditorPane globalStatsPane = (JEditorPane) globalScrollPane.getViewport().getView();
                setStatisticPane(globalStatsPane, "Global statistics", false, ridesTable);
            }
            JScrollPane filteredScrollPane = (JScrollPane) components[1];
            JEditorPane filteredStatsPane = (JEditorPane) filteredScrollPane.getViewport().getView();
            setStatisticPane(filteredStatsPane, "Filtered statistics", true, ridesTable);
        }
    }
    private static void setStatisticPane(JEditorPane statsPane, String name, boolean filtered, JTable ridesTable){
        RidesTableModel ridesTableModel = (RidesTableModel) ridesTable.getModel();
        ArrayList<Ride> rides = new ArrayList<>();
        if(filtered){
            for(int i = 0; i < ridesTable.getRowCount(); i++){
                int modelIndex = ridesTable.convertRowIndexToModel(i);
                rides.add(ridesTableModel.getEntity(modelIndex));
            }
        }
        else{
            for(int i = 0; i < ridesTableModel.getRowCount(); i++){
            rides.add(ridesTableModel.getEntity(i));
            }
        }
        String ridesCount = String.format("%,d", rides.size());
        String totalPrice = String.format("%.2f", ridesTableModel.totalPrice(rides));
        String totalDistance = String.format("%.2f", ridesTableModel.totalDistance(rides));
        String averagePrice = String.format("%.2f", ridesTableModel.averagePrice(rides));
        String averageDistance = String.format("%.2f", ridesTableModel.averageDistance(rides));

        statsPane.setText("<html><h3>" + name + ":</h3><ul>" +
                "<li>Rides count: " + ridesCount + "</li><br>" +
                "<li>Total price: " + totalPrice + "</li>" +
                "<li>Total distance: " + totalDistance + "</li><br>" +
                "<li>Average price: " + averagePrice + "</li>" +
                "<li>Average distance: " + averageDistance + "</li>" +
                "</ul></html>");
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

    private JPanel createToolBar(JTable ridesTable, CategoryListModel categoryListModel, JPanel statisticsPanel) {
        JPanel toolbarPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        //gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        toolbarPanel.add(createActionToolbar(), gbc);

        var rowSorter = new TableRowSorter<>((RidesTableModel)ridesTable.getModel());
        var ridesTableFilter = new RidesTableFilter(rowSorter);
        ridesTable.setRowSorter(rowSorter);

        var currencyFilter = createCurrencyFilter(ridesTableFilter, ridesTable, statisticsPanel);
        var categoryFilter = createCategoryFilter(ridesTableFilter, categoryListModel, ridesTable, statisticsPanel);// tu sa vytvara ten filter
        var scroll = new JScrollPane(categoryFilter);// tu sa vytvara scroll
        var distanceFromFilter = new JTextField();
        var distanceToFilter = new JTextField();
        var dateFromFilter = new LocalDateTimeModel();
        var dateToFilter = new LocalDateTimeModel();
        var dateFromPicker = new JDatePicker(dateFromFilter);
        var dateToPicker = new JDatePicker(dateToFilter);
        var resetFiltersButton = new JButton("Reset Filters");

        distanceFromFilter.setPreferredSize(new Dimension(60,20));
        distanceToFilter.setPreferredSize(new Dimension(60, 20));
        dateFromPicker.setPreferredSize(new Dimension(100, 20));
        dateToPicker.setPreferredSize(new Dimension(100, 20));
        scroll.setPreferredSize(new Dimension(140, 60));

        setActionListeners(ridesTableFilter, distanceFromFilter, distanceToFilter, dateFromFilter, dateToFilter,
                ridesTable, statisticsPanel);
        resetFiltersButton.addActionListener(e -> resetFilters(distanceFromFilter, distanceToFilter, currencyFilter,
                dateFromFilter, dateToFilter, categoryFilter));

        gbc.gridy = 1;
        toolbarPanel.add(createFilterToolbar(new JLabel("Date from:"), dateFromPicker,
                new JLabel("Date to:"), dateToPicker,
                new JLabel("Category:"), scroll), gbc);

        gbc.gridy = 2;
        toolbarPanel.add(createFilterToolbar(new JLabel("Distance from:"), distanceFromFilter,
                new JLabel("Distance to:"), distanceToFilter,
                new JLabel("Currency:"), currencyFilter, resetFiltersButton), gbc);

        return toolbarPanel;
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

    private JToolBar createFilterToolbar(Component ... components) {
        var toolbar = new JToolBar();
        toolbar.setLayout(new FlowLayout());
        toolbar.setFloatable(false);

        for (var component: components) {
            toolbar.add(component);
        }

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
