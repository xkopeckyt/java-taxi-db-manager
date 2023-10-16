package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.time.LocalDateTime;

public class WrongDateDialog extends EntityDialog<LocalDateTime>{

    private final JLabel labelLicence = new JLabel("Ride will not be added!\n Licence invalid that day!");
    private final LocalDateTime dateTime;

    public WrongDateDialog(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        addFields();
    }

    private void addFields() {
        addLabel(labelLicence);
    }

    @Override
    LocalDateTime getEntity() {
        return dateTime;
    }
}
