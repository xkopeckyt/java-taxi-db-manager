package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.ui.model.LocalDateTimeModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;


import java.time.LocalDateTime;

public class DrivingLicenceDialog extends EntityDialog<DrivingLicence> {
    private final DateModel<LocalDateTime> to = new LocalDateTimeModel();
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
    DrivingLicence getEntity() {
        drivingLicence.setTo(to.getValue());
        return drivingLicence;
    }
}
