package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.business.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DrivingLicence;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.ui.dialog.RideDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;



public class NewRideAction extends AbstractAction {
    private final JTable ridesTable;
    private final CategoryListModel categoryListModel;
    private final DrivingLicence licence;
    private final TemplateListModel templates;

    public NewRideAction(JTable ridesTable, CategoryListModel categoryListModel,
                         DrivingLicence licence, TemplateListModel templates) {
        super("New Ride", Icons.NEW_ICON);
        putValue(SHORT_DESCRIPTION, "Show Create new ride Dialog");
        putValue(MNEMONIC_KEY, KeyEvent.VK_N);

        this.ridesTable = ridesTable;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
        this.templates = templates;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var emptyRide = Ride.emptyRide(categoryListModel);
        var result = RideDialog.showDialog("New Ride", emptyRide, categoryListModel, licence, templates, false);
        if (result.isPresent()) {
            var ridesTableModel = (RidesTableModel) ridesTable.getModel();
            ridesTableModel.addRow(result.get());
        }
    }
}