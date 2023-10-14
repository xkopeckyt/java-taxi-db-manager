package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.ui.dialog.DrivingLicenceDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditTechnicalLicenceAction extends AbstractAction {

    private final DrivingLicence drivingLicence;
    private final JFrame frame;
    private final JLabel label;
    public EditTechnicalLicenceAction(DrivingLicence drivingLicence, JFrame frame) {
        super("Show/Edit Technical Licence");
        putValue(SHORT_DESCRIPTION, "Show/Edit Technical Licence validity date");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);

        this.drivingLicence = drivingLicence;
        this.frame = frame;
        this.label = new JLabel("DRIVING LICENCE IS NOT VALID TODAY!!!");
        label.setForeground(Color.RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        if (!drivingLicence.getValid()) {
            addLabel(label);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean oldValidation = drivingLicence.getValid();
        var dialog = new DrivingLicenceDialog(drivingLicence);
        dialog.show(new JTable(), "Update driving licence");

        if (oldValidation && !drivingLicence.checkLicence()) {
            drivingLicence.setValid(drivingLicence.checkLicence());
            addLabel(label);
        } else if (!oldValidation && drivingLicence.checkLicence()) {
            drivingLicence.setValid(drivingLicence.checkLicence());
            removeLabel(label);
        }
    }

    private void addLabel(JLabel label) {
        frame.add(label, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }

    private void removeLabel(JLabel label) {
        frame.remove(label);
        frame.revalidate();
        frame.repaint();
    }
}
