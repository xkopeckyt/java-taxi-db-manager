package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.dialog.RideDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;



public class NewRideAction extends AbstractAction {
    private final JTable ridesTable;
    private final TestDataGenerator testDataGenerator;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;
    private final Map<String, Ride> templates;

    public NewRideAction(JTable ridesTable, TestDataGenerator testDataGenerator, ListModel<Category> categoryListModel,
                         DrivingLicence licence, Map<String, Ride> templates) {
        super("New Ride", Icons.NEW_ICON);
        putValue(SHORT_DESCRIPTION, "Show Create new ride Dialog");
        putValue(MNEMONIC_KEY, KeyEvent.VK_N);

        this.ridesTable = ridesTable;
        this.testDataGenerator = testDataGenerator;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
        this.templates = templates;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var result = RideDialog.showDialog("New Ride", testDataGenerator.createTestRide(), categoryListModel, licence, templates);
        if (result.isPresent()) {
            var ridesTableModel = (RidesTableModel) ridesTable.getModel();
            ridesTableModel.addRow(result.get());
        }
    }
}