package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DrivingLicence;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.ui.dialog.RideDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

public class EditRideAction extends AbstractAction {
    private final JTable ridesTable;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;
    private final TemplateListModel templates;
    public EditRideAction(JTable ridesTable, ListModel<Category> categoryListModel, DrivingLicence licence, TemplateListModel templates) {
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
        var result = RideDialog.showDialog("Edit Ride", ride, categoryListModel, licence, templates, false);
        if (result.isPresent() && licence.checkDate(result.get().getRideDateTime().toLocalDate())) {
            // Update the ride in the table model
            ridesTableModel.updateRow(result.get());
        }
    }
}