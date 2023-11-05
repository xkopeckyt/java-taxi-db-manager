package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Filter;
import cz.muni.fi.pv168.project.ui.model.LocalDateTimeModel;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.time.LocalDateTime;

public class FilterDialog extends EntityDialog <Filter> {

    private final JTextField distanceFieldFrom = new JTextField();
    private final JTextField distanceFieldTo = new JTextField();
    private final DateModel<LocalDateTime> dateTimeModelFrom = new LocalDateTimeModel();
    private final DateModel<LocalDateTime> dateTimeModelTo = new LocalDateTimeModel();
    private final ComboBoxModel<Currency> currencyModel = new DefaultComboBoxModel<>(Currency.values());
    private final ComboBoxModel<Category> categoryModel;
    private final Filter filter;

    public FilterDialog(Filter filter, ListModel<Category> categoryModel) {
        this.filter = filter;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        setValues();
        addFields();
    }

    private void setValues() {
        distanceFieldFrom.setText(String.valueOf(filter.getDistanceFrom()));
        distanceFieldTo.setText(String.valueOf(filter.getDistanceTo()));
        dateTimeModelFrom.setValue(filter.getDateTimeFrom());
        dateTimeModelTo.setValue(filter.getDateTimeTo());
        currencyModel.setSelectedItem(filter.getOriginalCurrency());
        categoryModel.setSelectedItem(filter.getCategory());
    }

    private void addFields() {
        add("Distance from:", distanceFieldFrom);
        add("Distance to:", distanceFieldTo);
        add("Currency:", new JComboBox<>(currencyModel));
        add("Date from:", new JDatePicker(dateTimeModelFrom));
        add("Date to:", new JDatePicker(dateTimeModelTo));
        add("Category:", new JComboBox<>(categoryModel));
    }

    @Override
    Filter getEntity() {
        filter.setDistanceFrom(Float.parseFloat(distanceFieldFrom.getText()));
        filter.setDistanceTo(Float.parseFloat(distanceFieldTo.getText()));
        filter.setOriginalCurrency((Currency) currencyModel.getSelectedItem());
        filter.setDateTimeFrom(dateTimeModelFrom.getValue());
        filter.setDateTimeTo(dateTimeModelTo.getValue());
        filter.setCategory((Category) categoryModel.getSelectedItem());
        return filter;
    }
}
