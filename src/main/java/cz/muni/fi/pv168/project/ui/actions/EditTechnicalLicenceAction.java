package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.ui.dialog.DrivingLicenceDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static javax.swing.JOptionPane.*;

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
        this.label = new JLabel("DRIVING LICENCE IS NOT VALID TODAY!!! UPDATE YOUR LICENCE!!!", SwingConstants.CENTER);
        label.setForeground(Color.RED);

        if (!drivingLicence.getValid()) {
            addLabel(label);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean oldValidation = drivingLicence.getValid();
        var dialog = new DrivingLicenceDialog(drivingLicence);
        var result = dialog.show(new JTable(), "Update driving licence", OK_CANCEL_OPTION, null);

        if (result.isPresent()) {
            drivingLicence.setTo(result.get());
            if (oldValidation && !drivingLicence.checkLicence()) {
                drivingLicence.setValid(drivingLicence.checkLicence());
                addLabel(label);
            } else if (!oldValidation && drivingLicence.checkLicence()) {
                drivingLicence.setValid(drivingLicence.checkLicence());
                removeLabel(label);
            }
        }
    }

    private void addLabel(JLabel label) {
        frame.add(label, BorderLayout.AFTER_LAST_LINE);
        frame.revalidate();
        frame.repaint();
    }

    private void removeLabel(JLabel label) {
        frame.remove(label);
        frame.revalidate();
        frame.repaint();
    }
}
