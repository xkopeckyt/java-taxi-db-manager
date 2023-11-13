package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.ui.dialog.RideDetailDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static javax.swing.JOptionPane.*;

public class ShowRideAction extends AbstractAction {
    private final JTable rideTable;
    private final TestDataGenerator testDataGenerator;

    public ShowRideAction(JTable showRideTable, TestDataGenerator testDataGenerator) {
        super("Show Ride", Icons.SHOW_ICON);
        putValue(SHORT_DESCRIPTION, "Show Ride Info");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));

        this.rideTable = showRideTable;
        this.testDataGenerator = testDataGenerator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new RideDetailDialog(testDataGenerator.createTestRide());
        dialog.show(rideTable, "Ride Detail", OK_OPTION, new String[]{"OK"});
    }
}