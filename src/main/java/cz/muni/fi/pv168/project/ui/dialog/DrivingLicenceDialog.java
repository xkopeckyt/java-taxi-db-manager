package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.ui.model.LocalDateModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class DrivingLicenceDialog extends EntityDialog<LocalDate> {
    private final LocalDateModel to = new LocalDateModel();
    private final DrivingLicence drivingLicence;

    public DrivingLicenceDialog(DrivingLicence drivingLicence) {
        this.drivingLicence = drivingLicence;
        setValues();
        addFields();
    }

    private void setValues() {
        to.setValue(drivingLicence.getTo());
    }

    private void addFields() {
        add("To:", new JDatePicker(to));
    }

    @Override
    LocalDate getEntity() {
        return to.getValue();
    }
}
