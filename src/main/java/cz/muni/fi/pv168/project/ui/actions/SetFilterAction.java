package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.dialog.FilterDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static javax.swing.JOptionPane.*;

public class SetFilterAction extends AbstractAction {
    private final JTable rideTable;
    private final TestDataGenerator testDataGenerator;
    private final ListModel<Category> categoryListModel;

    public SetFilterAction(JTable rideTable, TestDataGenerator testDataGenerator, ListModel<Category> categoryListModel) {
        super("Set Filter");
        putValue(SHORT_DESCRIPTION, "Show Set filter Dialog for rides");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);

        this.rideTable = rideTable;
        this.testDataGenerator = testDataGenerator;
        this.categoryListModel = categoryListModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new FilterDialog(testDataGenerator.createTestFilter(), categoryListModel);
        dialog.show(rideTable, "Filter Rides", OK_CANCEL_OPTION, null);
    }
}