package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DrivingLicence;
import cz.muni.fi.pv168.project.model.Ride;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class NewRideFromTemplateAction extends AbstractAction {
    /*private final JTable ridesTable;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;
    private final List <Ride> rideTemplates;*/
    public NewRideFromTemplateAction(JTable ridesTable, ListModel<Category> categoryListModel,
                                     DrivingLicence licence, List <Ride> rideTemplates) {
        super("New Ride from Template");
        putValue(SHORT_DESCRIPTION, "Show Create new ride Dialog with chosen Template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_T);

       /* this.ridesTable = ridesTable;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
        this.rideTemplates = rideTemplates;*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //System.out.println("Selected File: " + selectedFilePath);
        } else {
            System.out.println(this.getClass().getName());;
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
