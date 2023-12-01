package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.components.JStatusTextField;
import cz.muni.fi.pv168.project.ui.listeners.AbstractFieldListener;
import cz.muni.fi.pv168.project.ui.listeners.DecimalFieldListener;
import cz.muni.fi.pv168.project.ui.listeners.IntegerFieldListener;
import cz.muni.fi.pv168.project.ui.model.JDateTimePicker;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

public class RideDialog extends EntityDialog <Ride> {

    private final JStatusTextField distanceField = new JStatusTextField();
    private final JStatusTextField priceField = new JStatusTextField();
    private final JStatusTextField passengersCountField = new JStatusTextField();
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
    private DrivingLicence licence;
    private final DecimalFormat decimalFormat;
    public final int decimalPlaces = 2;

    public RideDialog(Ride ride, ListModel<Category> categoryModel, DrivingLicence licence,
                      boolean editMode, JButton okButton) {
        this.editMode = editMode;
        this.okButton = okButton;
        this.ride = ride;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.categoryComboBox = new JComboBox<>(this.categoryModel);
        this.labelLicence = new JLabel(" ");
        this.licence = licence;
        labelLicence.setForeground(Color.red);
        decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(decimalPlaces);

        this.loadTemplateButton = new JButton("Load Templates");
        loadTemplateButton.addActionListener(e -> fileChooser.showOpenDialog(null));

        this.saveTemplateButton = new JButton("Save As Template");
        saveTemplateButton.addActionListener(e -> fileChooser.showSaveDialog(null));

        this.resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> setValues());

        datePicker.getEditor().setEditable(false);

        addListeners();
        setValues();
        addFields();

        checkValidDate();
        checkFormValidity();
    }

    private void checkValidDate() {
        validDate = licence.checkDate(datePicker.getLocalDate());
        setBackground(datePicker.getEditor(), validDate);
        labelLicence.setText(validDate ? " " : "Licence is invalid this day");
    }

    public void addListeners() {
        this.datePicker.addActionListener(e -> {
            checkValidDate();
            checkFormValidity();
        });

        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFormValidity();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFormValidity();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFormValidity();
            }
        };
        datePicker.addActionListener(e -> checkFormValidity());

        addListenersToField(passengersCountField, new IntegerFieldListener(passengersCountField), listener);
        addListenersToField(distanceField, new DecimalFieldListener(distanceField), listener);
        addListenersToField(priceField, new DecimalFieldListener(priceField), listener);
    }

    private void addListenersToField(JStatusTextField field, AbstractFieldListener fieldLis, DocumentListener docLis) {
        field.addFieldListener(fieldLis);
        field.addOnChangeEvent(this::setBackground);
        field.addOnChangeEvent(this::checkFormValidity);
        field.getDocument().addDocumentListener(docLis);
    }

    public void checkFormValidity() {
        okButton.setEnabled(distanceField.isValid() && priceField.isValid() && passengersCountField.isValid() &&
                            datePicker.getLocalDate() != null && validDate);
    }

    private void setBackground(JComponent comp, boolean isValid) {
        if (isValid) {
            comp.setBackground(Color.WHITE);
        } else {
            comp.setBackground(Color.decode("#ff4040"));
        }
    }

    public static Optional<Ride> showDialog(String name, Ride template, ListModel<Category> categoryListModel, DrivingLicence licence) {
        var okButton = DialogUtils.createButton("Ok");
        var dialog = new RideDialog(template, categoryListModel, licence, false, okButton);
        return dialog.show(null, name, OK_CANCEL_OPTION, new Object[]{ okButton, "Cancel"});
    }

    private void setValues() {
        priceField.setText(decimalFormat.format(ride.getPrice()));
        currencyModel.setSelectedItem(ride.getOriginalCurrency());
        distanceField.setText(decimalFormat.format(ride.getDistance()));
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
        ride.setPrice(textToFloat(priceField.getText()));
        ride.setOriginalCurrency((Currency) currencyModel.getSelectedItem());
        ride.setDistance(textToFloat(distanceField.getText()));
        ride.setDateTime(datePicker.getLocalDateTime());
        ride.setCategory((Category) categoryModel.getSelectedItem());
        ride.setPassengersCount(Integer.parseInt(passengersCountField.getText()));
        return ride;
    }

    private float textToFloat(String text) {
        return Float.parseFloat(text.replace(',', '.'));
    }
}
