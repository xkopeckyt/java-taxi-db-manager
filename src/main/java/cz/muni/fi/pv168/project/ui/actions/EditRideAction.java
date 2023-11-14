package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.dialog.RideDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

public class EditRideAction extends AbstractAction {
    private final JTable ridesTable;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;
    private final Map<String, Ride> templates;
    public EditRideAction(JTable ridesTable, ListModel<Category> categoryListModel, DrivingLicence licence, Map<String, Ride> templates) {
        super("Edit Ride", Icons.EDIT_ICON);
        putValue(SHORT_DESCRIPTION, "Show Edit Ride Dialog");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));

        this.ridesTable = ridesTable;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
        this.templates = templates;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var ridesTableModel = (RidesTableModel)ridesTable.getModel();
        Ride ride = ridesTableModel.getEntity(ridesTable.getSelectedRow());
        var result = RideDialog.showDialog("New Ride", ride, categoryListModel, licence, templates);
        if (result.isPresent() && licence.checkDate(result.get().getDateTime().toLocalDate())) {
            // Update the ride in the table model
            ridesTableModel.updateRow(result.get());
        }
    }
}