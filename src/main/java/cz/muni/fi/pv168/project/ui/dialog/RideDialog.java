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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
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
    private Ride ride;
    private final JButton loadTemplateButton;
    private final JButton saveTemplateButton;
    private final JButton resetButton;
    private boolean validDate = true;
    private final JLabel labelLicence;
    private final boolean editMode;
    private boolean templateMode;
    private final JButton okButton;
    private final Map<String, Ride> templates;
    private final DrivingLicence licence;
    private final ListModel<Category> categoryListModel;
    private final DecimalFormat decimalFormat;
    public final int decimalPlaces = 2;

    public RideDialog(Ride ride, ListModel<Category> categoryModel, DrivingLicence licence,
                      boolean editMode, JButton okButton, Map<String, Ride> templates, boolean templateMode) {
        this.templateMode = templateMode;
        this.editMode = editMode;
        this.okButton = okButton;
        this.ride = ride;
        this.categoryListModel = categoryModel;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryListModel);
        this.categoryComboBox = new JComboBox<>(this.categoryModel);
        this.templates = templates;
        this.licence = licence;
        this.labelLicence = new JLabel(" ");
        labelLicence.setForeground(Color.red);
        decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(decimalPlaces);

        this.loadTemplateButton = new JButton("Load Templates");
        this.saveTemplateButton = new JButton("Save As Template");

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

        loadTemplateButton.addActionListener(e -> {
            if (!templates.isEmpty()) {
                var loadTemplatesDialog = new LoadTemplateDialog(templates.keySet());
                var loadResult = loadTemplatesDialog.show(new JTable(), "Load Template", OK_CANCEL_OPTION, null);
                loadResult.ifPresent(s -> {
                    ride = templates.get(s);
                    templateMode = true;
                    setValues();
                });
            } else {
                var emptyTemplatesDialog = new EmptyTemplateDialog();
                emptyTemplatesDialog.show(new JTable(), "Empty Templates", OK_OPTION, new Object[]{"OK"});
            }
        });

        saveTemplateButton.addActionListener(e ->  {
            var templateResult = SaveTemplateDialog.showDialog(templates);
            templateResult.ifPresent(s ->
                templates.put(s,
                    new Ride(textToBigDecimal(distanceField.getText()),
                            (datePicker.getLocalDateTime() == null) ? LocalDateTime.now() : datePicker.getLocalDateTime(),
                            textToBigDecimal(priceField.getText()),
                            (Currency) currencyModel.getSelectedItem(),
                            (Category) categoryComboBox.getSelectedItem(),
                            (!passengersCountField.getText().isEmpty()) ? Integer.parseInt(passengersCountField.getText()) : 0
                    )
                )
            );
        });

        datePicker.addActionListener(e -> checkFormValidity());

        addListenersToField(passengersCountField, new IntegerFieldListener(passengersCountField), listener);
        addListenersToField(distanceField, new DecimalFieldListener(distanceField), listener);
        addListenersToField(priceField, new DecimalFieldListener(priceField), listener);
    }

    private void addListenersToField(JStatusTextField field, AbstractFieldListener fieldLis, DocumentListener docLis) {
        field.addOnChangeEvent(this::setBackground);
        field.addOnChangeEvent(this::checkFormValidity);
        field.getDocument().addDocumentListener(docLis);
        field.addFieldListener(fieldLis);
    }

    public void checkFormValidity() {
        okButton.setEnabled(distanceField.isValid() && priceField.isValid() && passengersCountField.isValid() &&
                            datePicker.getLocalDate() != null && validDate);
        saveTemplateButton.setEnabled((distanceField.isValid() || distanceField.getText().isEmpty())
                                        && (priceField.isValid() || priceField.getText().isEmpty())
                                        && (passengersCountField.isValid() || passengersCountField.getText().isEmpty()));
    }

    private void setBackground(JComponent comp, boolean isValid) {
        if (isValid) {
            comp.setBackground(Color.WHITE);
        } else {
            comp.setBackground(Color.decode("#ff4040"));
        }
    }

    public static Optional<Ride> showDialog(String name, Ride template, ListModel<Category> categoryListModel,
                                            DrivingLicence licence, Map<String, Ride> templates, boolean templateMode) {
        var okButton = DialogUtils.createButton("Ok");
        var dialog = new RideDialog(template, categoryListModel, licence, false, okButton, templates, templateMode);
        return dialog.show(null, name, OK_CANCEL_OPTION, new Object[]{ okButton, "Cancel"});
    }

    private void setValues() {
        priceField.setText((templateMode) ? decimalFormat.format(ride.getPrice()) : "");
        currencyModel.setSelectedItem((templateMode) ? ride.getOriginalCurrency() : Currency.EUR);
        distanceField.setText((templateMode) ? decimalFormat.format(ride.getDistance()) : "");
        datePicker.setLocalDateTime((templateMode) ? ride.getRideDateTime() : LocalDateTime.now());
        categoryModel.setSelectedItem((templateMode) ? ride.getCategory() :categoryListModel.getElementAt(0));
        passengersCountField.setText((templateMode) ? String.valueOf(ride.getPassengersCount()) : "");
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
        ride.setPrice(textToBigDecimal(priceField.getText()));
        ride.setOriginalCurrency((Currency) currencyModel.getSelectedItem());
        ride.setDistance(textToBigDecimal(distanceField.getText()));
        ride.setRideDateTime(datePicker.getLocalDateTime());
        ride.setCategory((Category) categoryModel.getSelectedItem());
        ride.setPassengersCount(Integer.parseInt(passengersCountField.getText()));
        return ride;
    }

    private BigDecimal textToBigDecimal(String text) {
        if (text.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(Double.parseDouble(text.replace(',', '.')));
    }
}
