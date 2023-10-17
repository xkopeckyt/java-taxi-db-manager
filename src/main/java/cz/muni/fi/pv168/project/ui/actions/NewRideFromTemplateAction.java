package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.ui.dialog.RideDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewRideFromTemplateAction extends AbstractAction {
    private final JTable ridesTable;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;
    TestDataGenerator testDataGenerator;

    public NewRideFromTemplateAction(JTable ridesTable, ListModel<Category> categoryListModel,
                                     DrivingLicence licence, TestDataGenerator testDataGenerator) {
        super("New Ride from Template");
        putValue(SHORT_DESCRIPTION, "Show Create new ride Dialog with chosen Template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_T);
        this.testDataGenerator = testDataGenerator;

        this.ridesTable = ridesTable;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            var ridesTableModel = (RidesTableModel) ridesTable.getModel();
            var rideDialog = new RideDialog(testDataGenerator.createTestRide(), categoryListModel, licence);
            var rideResult = rideDialog.show(ridesTable, "New Ride");

            if (rideResult.isPresent() && licence.checkDate(rideResult.get().getDateTime())) {
                ridesTableModel.addRow(rideResult.get());
            } else {
                System.out.println(this.getClass().getName());
            }
            /*if (templateResult.isPresent() && rideTemplates.size() != 0) {
            var ridesTableModel = (RidesTableModel) ridesTable.getModel();
            if (!licence.checkDate(templateResult.get().getDateTime())) {
                var wrongDateDialog = new WrongDateDialog(templateResult.get().getDateTime());
                wrongDateDialog.show(new JTable(), "Invalid date");
            }
            var rideDialog = new RideDialog(templateResult.get(), categoryListModel, rideTemplates, licence);
            var rideResult = rideDialog.show(ridesTable, "New Ride");
            if (rideResult.isPresent() && licence.checkDate(rideResult.get().getDateTime())) {
                ridesTableModel.addRow(rideResult.get());
            }*/
        }
    }
}
