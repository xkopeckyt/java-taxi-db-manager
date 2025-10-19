package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Ride;

import javax.swing.*;

public class RideDetailDialog extends EntityDialog <Ride> {

    private final JTextField distanceField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField passengersCountField = new JTextField();
    private final JTextField currencyField= new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField dateTimeField = new JTextField();
    private final Ride ride;

    public RideDetailDialog(Ride ride) {
        this.ride = ride;
        setValues();
        addFields();
    }

    private void setValues() {
        priceField.setText(String.valueOf(ride.getPrice()));
        priceField.setEditable(false);
        currencyField.setText(String.valueOf(ride.getOriginalCurrency()));
        currencyField.setEditable(false);
        distanceField.setText(String.valueOf(ride.getDistance()));
        distanceField.setEditable(false);
        dateTimeField.setText(String.valueOf(ride.getRideDateTime()));
        dateTimeField.setEditable(false);
        categoryField.setText(String.valueOf(ride.getCategory()));
        categoryField.setEditable(false);
        passengersCountField.setText(String.valueOf(ride.getPassengersCount()));
        passengersCountField.setEditable(false);
    }

    private void addFields() {
        add("Price:", priceField);
        add("Original currency:", currencyField);
        add("Distance:", distanceField);
        add("Date & Time", dateTimeField);
        add("Category:", categoryField);
        add("Passengers count:", passengersCountField);
    }

    @Override
    Ride getEntity() {
        return ride;
    }
}
