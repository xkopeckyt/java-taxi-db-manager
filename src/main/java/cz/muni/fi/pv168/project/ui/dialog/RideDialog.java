package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.components.JStatusTextField;
import cz.muni.fi.pv168.project.ui.listeners.DecimalFieldListener;
import cz.muni.fi.pv168.project.ui.model.JDateTimePicker;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

public class RideDialog extends EntityDialog <Ride> {

    private final JTextField distanceField = new JTextField();
    private final JStatusTextField priceField = new JStatusTextField();
    private final JTextField passengersCountField = new JTextField();
    private final ComboBoxModel<Currency> currencyModel = new DefaultComboBoxModel<>(Currency.values());
    private final ComboBoxModel<Category> categoryModel;
    private final JDateTimePicker datePicker = new JDateTimePicker();
    private final JComboBox<Category> categoryComboBox;
    private final Ride ride;
    private final JButton loadTemplateButton;
    private final JButton saveTemplateButton;
    private final JButton resetButton;
    private boolean validDate = true;
    private final JLabel labelLicence;
    private final JFileChooser fileChooser = new JFileChooser();
    private final boolean editMode;
    private final JButton okButton;

    public RideDialog(Ride ride, ListModel<Category> categoryModel, DrivingLicence licence,
                      boolean editMode, JButton okButton) {
        this.editMode = editMode;
        this.okButton = okButton;
        this.ride = ride;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.categoryComboBox = new JComboBox<>(this.categoryModel);
        this.labelLicence = new JLabel("");
        labelLicence.setForeground(Color.red);


        this.loadTemplateButton = new JButton("Load Templates");
        loadTemplateButton.addActionListener(e -> fileChooser.showOpenDialog(null));

        this.saveTemplateButton = new JButton("Save As Template");
        saveTemplateButton.addActionListener(e ->  {
        fileChooser.showSaveDialog(null);
                /*new Ride(Float.parseFloat(distanceField.getText()),
                dateTimeModel.getValue(),
                Float.parseFloat(priceField.getText()),
                (Currency) currencyModel.getSelectedItem(),
                (Category) categoryComboBox.getSelectedItem(),
                Integer.parseInt(passengersCountField.getText())))*/
        });
        this.resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> setValues());


        this.datePicker.addActionListener(e -> {
            if ((validDate && !licence.checkDate(datePicker.getLocalDate())) ||
                    (!validDate && licence.checkDate(datePicker.getLocalDate()))) {
                validDate = !validDate;
                refreshButtonLabel();
            }
        });

        setValues();
        addFields();
        addListeners();

        if (!licence.checkDate(datePicker.getLocalDate())) {
            validDate = !validDate;
            refreshButtonLabel();
        }
    }

    public void addListeners() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshButtonLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshButtonLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshButtonLabel();
            }
        };
        distanceField.getDocument().addDocumentListener(listener);
        datePicker.addActionListener(e -> refreshButtonLabel());
        passengersCountField.getDocument().addDocumentListener(listener);

        //priceField.getDocument().addDocumentListener(listener);
        priceField.addFieldListener(new DecimalFieldListener(priceField));
    }

    public void refreshButtonLabel() {
        if (distanceField.getText().isEmpty() || priceField.getText().isEmpty() ||
            datePicker.getLocalDate() == null || passengersCountField.getText().isEmpty() || !validDate) {
            okButton.setEnabled(false);
            labelLicence.setText("Invalid licence date or empty field!");
        } else {
            okButton.setEnabled(true);
            labelLicence.setText("");
        }
    }

    public static Optional<Ride> showDialog(String name, Ride template, ListModel<Category> categoryListModel, DrivingLicence licence) {
        var okButton = DialogUtils.createButton("Ok");
        var dialog = new RideDialog(template, categoryListModel, licence, false, okButton);
        return dialog.show(null, name, OK_CANCEL_OPTION, new Object[]{ okButton, "Cancel"});
    }

    private void setValues() {
        priceField.setText(String.valueOf(ride.getPrice()));
        currencyModel.setSelectedItem(ride.getOriginalCurrency());
        distanceField.setText(String.valueOf(ride.getDistance()));
        datePicker.setLocalDateTime(ride.getDateTime());
        categoryModel.setSelectedItem(ride.getCategory());
        passengersCountField.setText(String.valueOf(ride.getPassengersCount()));
    }

    private void addFields() {
        add("Price:", priceField);
        add("Currency:", new JComboBox<>(currencyModel));
        add("Distance:", distanceField);
        add("Date & Time", datePicker);
        addSeparator();
        addLabel(labelLicence);
        add("Category:", categoryComboBox);
        add("Passengers count:", passengersCountField);
        if(!editMode) {
            addButton(loadTemplateButton);
            addButton(saveTemplateButton);
        } else{
            addButton(resetButton);
        }
    }

    @Override
    Ride getEntity() {
        ride.setPrice(Float.parseFloat(priceField.getText()));
        ride.setOriginalCurrency((Currency) currencyModel.getSelectedItem());
        ride.setDistance(Float.parseFloat(distanceField.getText()));
        ride.setDateTime(datePicker.getLocalDateTime());
        ride.setCategory((Category) categoryModel.getSelectedItem());
        ride.setPassengersCount(Integer.parseInt(passengersCountField.getText()));
        return ride;
    }

}
