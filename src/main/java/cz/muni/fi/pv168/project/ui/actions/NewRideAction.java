package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.ui.dialog.RideDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static javax.swing.JOptionPane.*;


public class NewRideAction extends AbstractAction {
    private final JTable ridesTable;
    private final TestDataGenerator testDataGenerator;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;

    public NewRideAction(JTable ridesTable, TestDataGenerator testDataGenerator, ListModel<Category> categoryListModel,
                         DrivingLicence licence) {
        super("New Ride");
        putValue(SHORT_DESCRIPTION, "Show Create new ride Dialog");
        putValue(MNEMONIC_KEY, KeyEvent.VK_N);

        this.ridesTable = ridesTable;
        this.testDataGenerator = testDataGenerator;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var ridesTableModel = (RidesTableModel) ridesTable.getModel();
        var dialog = new RideDialog(testDataGenerator.createTestRide(), categoryListModel, licence, false);
        var result = dialog.show(ridesTable, "New Ride", OK_CANCEL_OPTION, null);
        if (result.isPresent() && licence.checkDate(result.get().getDateTime())) {
            ridesTableModel.addRow(result.get());
        }
    }
}