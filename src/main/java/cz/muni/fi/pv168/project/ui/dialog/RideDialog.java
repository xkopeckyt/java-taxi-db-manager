package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.LocalDateTimeModel;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.time.LocalDateTime;

public class RideDialog extends EntityDialog <Ride> {

    private final JTextField distanceField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField passengersCountField = new JTextField();
    private final ComboBoxModel<Currency> currencyModel = new DefaultComboBoxModel<>(Currency.values());
    private final ComboBoxModel<Category> categoryModel;
    private final DateModel<LocalDateTime> dateTimeModel = new LocalDateTimeModel();
    private final Ride ride;

    public RideDialog(Ride ride, ListModel<Category> categoryModel) {
        this.ride = ride;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        setValues();
        addFields();
    }

    private void setValues() {
        priceField.setText(String.valueOf(ride.getPrice()));
        currencyModel.setSelectedItem(ride.getOriginalCurrency());
        distanceField.setText(String.valueOf(ride.getDistance()));
        dateTimeModel.setValue(ride.getDateTime());
        categoryModel.setSelectedItem(ride.getCategory());
        passengersCountField.setText(String.valueOf(ride.getPassengersCount()));
    }

    private void addFields() {
        add("Price:", priceField);
        add("Currency:", new JComboBox<>(currencyModel));
        add("Distance:", distanceField);
        add("Date & Time", new JDatePicker(dateTimeModel));
        add("Category:", new JComboBox<>(categoryModel));
        add("Passengers count:", passengersCountField);
    }

    @Override
    Ride getEntity() {
        ride.setPrice(Float.parseFloat(priceField.getText()));
        ride.setOriginalCurrency((Currency) currencyModel.getSelectedItem());
        ride.setDistance(Float.parseFloat(distanceField.getText()));
        ride.setDateTime(dateTimeModel.getValue());
        ride.setCategory((Category) categoryModel.getSelectedItem());
        ride.setPassengersCount(Integer.parseInt(passengersCountField.getText()));
        return ride;
    }
}
