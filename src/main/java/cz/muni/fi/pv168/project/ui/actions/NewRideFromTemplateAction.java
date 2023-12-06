package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DrivingLicence;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.ui.dialog.EmptyTemplateDialog;
import cz.muni.fi.pv168.project.ui.dialog.LoadTemplateDialog;
import cz.muni.fi.pv168.project.ui.dialog.RideDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

import static javax.swing.JOptionPane.*;

public class NewRideFromTemplateAction extends AbstractAction {
    private final JTable ridesTable;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;
    private final TemplateListModel templates;

    public NewRideFromTemplateAction(JTable ridesTable, ListModel<Category> categoryListModel,
                                     DrivingLicence licence, TemplateListModel templates) {
        super("New Ride from Template", Icons.NEW_TEMPLATE_ICON);
        putValue(SHORT_DESCRIPTION, "Show Create new ride Dialog with chosen Template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_T);

        this.ridesTable = ridesTable;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
        this.templates = templates;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(templates.getSize() == 0)) {
            var loadTemplatesDialog = new LoadTemplateDialog(templates.getNames());
            var loadResult = loadTemplatesDialog.show(new JTable(), "Load Template", OK_CANCEL_OPTION, null);

            if (loadResult.isPresent()) {
                var template = templates.getTemplate(loadResult.get());
                var ride = new Ride(template.getDistance(), template.getTemplateDateTime(), template.getPrice(),
                        template.getOriginalCurrency(), template.getCategory(), template.getPassengersCount());
                var addRideDialogResult = RideDialog.showDialog("Add Ride", ride, categoryListModel, licence, templates, false);
                if (addRideDialogResult.isPresent()) {
                    var ridesTableModel = (RidesTableModel) ridesTable.getModel();
                    ridesTableModel.addRow(addRideDialogResult.get());
                }
            }
        } else {
            var dialog = new EmptyTemplateDialog();
            dialog.show(new JTable(), "Empty Templates", OK_OPTION, new Object[]{"OK"});
        }


    }
}
