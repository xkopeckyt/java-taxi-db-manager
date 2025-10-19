package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.ui.actions.*;
import cz.muni.fi.pv168.project.ui.filters.RidesTableFilter;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCategoryValues;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCurrencyValues;
import cz.muni.fi.pv168.project.ui.filters.components.FilterComboboxBuilder;
import cz.muni.fi.pv168.project.ui.filters.components.FilterListModelBuilder;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.JDateTimePicker;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.panels.RideTablePanel;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CurrencyRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterCategoryValuesRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterCurrencyValuesRenderer;
import cz.muni.fi.pv168.project.util.Either;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDateTime;
import java.util.*;

public class MainWindow {
    private static final int WIDTH = 950;
    private static final int HEIGHT = 600;
    private static final int MIN_WIDTH = 950;
    private static final int MIN_HEIGHT = 400;
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
    private final Action editTemplatesAction;
    private final Action aboutApplicationAction;
    private final Action quitAction;
    private final Action nuclearQuitAction;
    private final RidesTableModel ridesTableModel;
    private final CategoryListModel categoryListModel;
    private final TemplateListModel templateListModel;

    public MainWindow(DependencyProvider dependencyProvider) {
        frame = createFrame();
        var testDataGenerator = new TestDataGenerator();
        this.categoryListModel = new CategoryListModel(dependencyProvider.getCategoryCrudService());
        this.ridesTableModel = new RidesTableModel(dependencyProvider.getRideCrudService());
        this.templateListModel = new TemplateListModel(dependencyProvider.getTemplateCrudService());
        var ridesPanel = new RideTablePanel(ridesTableModel, categoryListModel, this::changeActionState);
        var licence = testDataGenerator.createTestDrivingLicence();

        newRideAction = new NewRideAction(ridesPanel.getTable(), categoryListModel, licence, templateListModel);
        newRideFromTemplateAction = new NewRideFromTemplateAction(ridesPanel.getTable(), categoryListModel, licence, templateListModel);
        showRideAction = new ShowRideAction(ridesPanel.getTable());
        editRideAction = new EditRideAction(ridesPanel.getTable(), categoryListModel, licence, templateListModel);
        deleteRideAction = new DeleteRideAction(ridesPanel.getTable());
        importDataAction = new ImportDataAction(ridesTableModel, dependencyProvider.getImportService(), this::refresh);
        exportDataAction = new ExportDataAction(ridesPanel.getTable(), dependencyProvider.getExportService());
        editTechnicalLicenceAction = new EditTechnicalLicenceAction(licence, frame);
        editCategoriesAction = new EditCategoriesAction(categoryListModel, ridesPanel.getTable(), this::refresh);
        editTemplatesAction = new EditTemplatesAction(templateListModel, categoryListModel, licence);
        aboutApplicationAction = new AboutApplicationAction();
        quitAction = new QuitAction();
        nuclearQuitAction = new NuclearQuitAction(dependencyProvider.getDatabaseManager());

        changeActionState(0);

        frame.setJMenuBar(createMenuBar());

        var tabbedPane = new JTabbedPane();

        JPanel mainPanel = new JPanel(new GridLayout(1,1));
        ridesPanel.getTable().setComponentPopupMenu(createRidesTablePopupMenu());
        mainPanel.add(new JScrollPane(ridesPanel.getTable())); //, BorderLayout.CENTER

        JPanel secondaryPanel = new JPanel(new GridLayout(1,1));
        JPanel statisticsPanel = createStatisticsPanel(ridesPanel.getTable());
        secondaryPanel.add(statisticsPanel);
        ridesTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateStatisticsPanel(statisticsPanel, ridesPanel.getTable(), true);
                    }
                });
            }
        });

        var actionToolbar = createActionToolbar();

        tabbedPane.addChangeListener(e -> {
            if(tabbedPane.getSelectedComponent() == secondaryPanel) {
                actionToolbar.setVisible(false);
            } else {
                actionToolbar.setVisible(true);
            }
        });

        tabbedPane.addTab("Rides (table)", mainPanel);
        tabbedPane.addTab("Statistics", secondaryPanel);

        frame.add(createToolBar(ridesPanel.getTable(), categoryListModel, statisticsPanel, actionToolbar), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.pack();
    }

    private void refresh() {
        categoryListModel.refresh();
        ridesTableModel.refresh();
        templateListModel.refresh();
    }

    private void setActionListeners(RidesTableFilter ridesTableFilter, JTextField distanceFrom, JTextField distanceTo,
                                    JDateTimePicker dateFrom, JDateTimePicker dateTo, JTextField priceFrom, JTextField priceTo,
                                    JTable ridesTable, JPanel statisticsPanel, JSpinner countFrom, JSpinner countTo) {
        distanceFrom.addActionListener(e -> {
            Float value = stringToFloat(distanceFrom.getText(), distanceFrom);
            ridesTableFilter.filterDistanceFrom(value);
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        distanceTo.addActionListener(e -> {
            Float value = stringToFloat(distanceTo.getText(), distanceTo);
            ridesTableFilter.filterDistanceTo(value);
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        dateFrom.addActionListener(e -> {
            var dateTime = dateFrom.getLocalDateTime();
            ridesTableFilter.filterDateFrom(Objects.requireNonNullElseGet(dateTime, () -> LocalDateTime.MIN.plusDays(1)));
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        dateTo.addActionListener(e -> {
            var dateTime = dateTo.getLocalDateTime();
            ridesTableFilter.filterDateTo(Objects.requireNonNullElseGet(dateTime, () -> LocalDateTime.MAX.minusDays(1)));
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        priceFrom.addActionListener(e -> {
            Float value = stringToFloat(priceFrom.getText(), priceFrom);
            ridesTableFilter.filterPriceFrom(value);
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        priceTo.addActionListener(e -> {
            Float value = stringToFloat(priceTo.getText(), priceTo);
            ridesTableFilter.filterPriceTo(value);
            updateStatisticsPanel(statisticsPanel, ridesTable, false);
        });

        countFrom.addChangeListener(e -> {ridesTableFilter.filterPassengerFromCount((int) countFrom.getValue());
        updateStatisticsPanel(statisticsPanel, ridesTable, false);});
        countTo.addChangeListener(e -> {ridesTableFilter.filterPassengerToCount((int) countTo.getValue());
        updateStatisticsPanel(statisticsPanel, ridesTable, false);});
    }

    private Float stringToFloat(String str, JComponent comp) {
        comp.setBackground(Color.white);
        if (str == null || str.isEmpty()) {
            return null;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception ex) {
            comp.setBackground(Color.red);
            return null;
        }
    }

    private void setFocusListeners(JTextField distanceFrom, JTextField distanceTo,
                                   JTextField priceFrom, JTextField priceTo) {
        var textFocusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField source = (JTextField) e.getSource();
                source.postActionEvent();
            }
        };

        distanceFrom.addFocusListener(textFocusListener);
        distanceTo.addFocusListener(textFocusListener);
        priceFrom.addFocusListener(textFocusListener);
        priceTo.addFocusListener(textFocusListener);
    }


    public void resetFilters (JTextField distanceFrom, JTextField distanceTo, JComboBox currency,
                              JDateTimePicker dateFrom, JDateTimePicker dateTo, JTextField priceFrom,
                              JTextField priceTo, JList category, JSpinner countFrom, JSpinner countTo) {
        distanceFrom.setText(null);
        distanceFrom.postActionEvent();
        distanceTo.setText(null);
        distanceTo.postActionEvent();
        currency.setSelectedIndex(0);
        category.setSelectedIndex(0);
        dateFrom.resetPicker();
        dateTo.resetPicker();
        priceFrom.setText(null);
        priceFrom.postActionEvent();
        priceTo.setText(null);
        priceTo.postActionEvent();
        countFrom.setValue(0);
        countTo.setValue(100);
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
        frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        return frame;
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
        menuBar.add(createQuit());
        /*
        menuBar.add(createNewRideMenu());
        menuBar.add(createDataMenu());
        menuBar.add(createTechnicalLicenceMenu());
        menuBar.add(createCategoryMenu());
        */
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(createAboutMenu());
        return menuBar;
    }

    private JMenu createQuit() {
        var menu = new JMenu("Quit");
        menu.setMnemonic('q');
        menu.add(quitAction);
        menu.add(nuclearQuitAction);
        return menu;
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

    private JPanel createToolBar(JTable ridesTable, CategoryListModel categoryListModel, JPanel statisticsPanel, JToolBar actionToolbar) {
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
        toolbarPanel.add(actionToolbar, gbc);

        var rowSorter = new TableRowSorter<>((RidesTableModel)ridesTable.getModel());
        var ridesTableFilter = new RidesTableFilter(rowSorter);
        ridesTable.setRowSorter(rowSorter);

        var currencyFilter = createCurrencyFilter(ridesTableFilter, ridesTable, statisticsPanel);
        var categoryFilter = createCategoryFilter(ridesTableFilter, categoryListModel, ridesTable, statisticsPanel);// tu sa vytvara ten filter
        var categoryScroll = new JScrollPane(categoryFilter);// tu sa vytvara scroll
        var distanceFromFilter = new JTextField();
        var distanceToFilter = new JTextField();
        var dateFromPicker = new JDateTimePicker();
        var dateToPicker = new JDateTimePicker();
        var priceFromFilter = new JTextField();
        var priceToFilter = new JTextField();
        var passengerCountFromFilter = new JSpinner();
        var passengerCountToFilter = new JSpinner();
        var resetFiltersButton = new JButton("Reset Filters");

        distanceFromFilter.setPreferredSize(new Dimension(60,25));
        distanceToFilter.setPreferredSize(new Dimension(60, 25));
        dateFromPicker.setPreferredSize(new Dimension(140, 25));
        dateToPicker.setPreferredSize(new Dimension(140, 25));
        priceFromFilter.setPreferredSize(new Dimension(60,25));
        priceToFilter.setPreferredSize(new Dimension(60, 25));
        categoryScroll.setPreferredSize(new Dimension(140, 60));
        currencyFilter.setPreferredSize(new Dimension(80, 25));
        passengerCountFromFilter.setPreferredSize(new Dimension(60, 25));
        passengerCountToFilter.setPreferredSize(new Dimension(60, 25));
        passengerCountToFilter.setValue(100);

        setActionListeners(ridesTableFilter, distanceFromFilter, distanceToFilter, dateFromPicker, dateToPicker,
                priceFromFilter, priceToFilter, ridesTable, statisticsPanel, passengerCountFromFilter, passengerCountToFilter);
        setFocusListeners(distanceFromFilter, distanceToFilter, priceFromFilter, priceToFilter);
        resetFiltersButton.addActionListener(e ->
        {
            resetFilters(distanceFromFilter, distanceToFilter, currencyFilter,
                    dateFromPicker, dateToPicker, priceFromFilter, priceToFilter,
                    categoryFilter, passengerCountFromFilter, passengerCountToFilter);
            dateFromPicker.setLocalDateTime(null);
            dateToPicker.setLocalDateTime(null);
        });

        gbc.gridy = 1;
        toolbarPanel.add(createFilterToolbar(new JLabel("Date from:"), dateFromPicker,
                new JLabel("Date to:"), dateToPicker,
                new JLabel("Category:"), categoryScroll,
                new JLabel("Currency:"), currencyFilter,
                resetFiltersButton), gbc);

        gbc.gridy = 2;
        toolbarPanel.add(createFilterToolbar(new JLabel("Distance from:"), distanceFromFilter,
                new JLabel("Distance to:"), distanceToFilter,
                new JLabel("Price from:"), priceFromFilter,
                new JLabel("Price to:"), priceToFilter,
                new JLabel("Passengers from:"), passengerCountFromFilter,
                new JLabel("Passengers to:"), passengerCountToFilter), gbc);

        return toolbarPanel;
    }

    private JToolBar createActionToolbar() {
        var toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(newRideAction);
        toolbar.add(newRideFromTemplateAction);
        toolbar.add(editRideAction);
        toolbar.add(deleteRideAction);
        toolbar.addSeparator();
        toolbar.add(importDataAction);
        toolbar.add(exportDataAction);
        toolbar.addSeparator();
        toolbar.add(editCategoriesAction);
        toolbar.add(editTemplatesAction);
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


    private void changeActionState(int count) {
        showRideAction.setEnabled(count == 1);
        editRideAction.setEnabled(count == 1);
        deleteRideAction.setEnabled(count >= 1);
    }
}
