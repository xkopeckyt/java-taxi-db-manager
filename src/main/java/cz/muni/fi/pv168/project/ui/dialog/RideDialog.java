package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.model.LocalDateTimeModel;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class RideDialog extends EntityDialog <Ride> {

    private final JTextField distanceField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField passengersCountField = new JTextField();
    private final ComboBoxModel<Currency> currencyModel = new DefaultComboBoxModel<>(Currency.values());
    private final ComboBoxModel<Category> categoryModel;
    private final DateModel<LocalDateTime> dateTimeModel = new LocalDateTimeModel();
    private final JDatePicker datePicker = new JDatePicker(dateTimeModel);
    private final JComboBox<Category> categoryComboBox;
    private final Ride ride;
    private final JButton loadTemplateButton;
    private final JButton saveTemplateButton;
    private boolean validDate = false;

    public RideDialog(Ride ride, ListModel<Category> categoryModel, List<Ride> rideTemplates, DrivingLicence licence) {
        this.ride = ride;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.categoryComboBox = new JComboBox<>(this.categoryModel);
        JLabel labelLicence = new JLabel("Ride will not be added, licence invalid!");
        labelLicence.setForeground(Color.red);


        this.loadTemplateButton = new JButton("Load Templates");
        loadTemplateButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                //System.out.println("Selected File: " + selectedFilePath);
            } else {
                System.out.println(this.getClass().getName());
            }
        });

        this.saveTemplateButton = new JButton("Save As Template");
        saveTemplateButton.addActionListener(e -> rideTemplates.add(new Ride(Float.parseFloat(distanceField.getText()),
                dateTimeModel.getValue(),
                Float.parseFloat(priceField.getText()),
                (Currency) currencyModel.getSelectedItem(),
                (Category) categoryComboBox.getSelectedItem(),
                Integer.parseInt(passengersCountField.getText()))));


        this.dateTimeModel.addChangeListener(e -> {
            if (validDate && !licence.checkDate(dateTimeModel.getValue())) {
                validDate = false;
                var wrongDateDialog = new WrongDateDialog(dateTimeModel.getValue());
                wrongDateDialog.show(new JTable(), "Invalid date!");
            } else if (!validDate && licence.checkDate(dateTimeModel.getValue())) {
                validDate = true;
            }
        });

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
        add("Date & Time", datePicker);
        add("Category:", categoryComboBox);
        add("Passengers count:", passengersCountField);
        addButton(loadTemplateButton);
        addButton(saveTemplateButton);
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
