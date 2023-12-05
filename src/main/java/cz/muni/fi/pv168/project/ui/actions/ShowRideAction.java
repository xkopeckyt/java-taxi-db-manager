package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.ui.dialog.RideDetailDialog;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static javax.swing.JOptionPane.*;

public class ShowRideAction extends AbstractAction {
    private final JTable ridesTable;

    public ShowRideAction(JTable ridesTable) {
        super("Show Ride", Icons.SHOW_ICON);
        putValue(SHORT_DESCRIPTION, "Show Ride Info");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));

        this.ridesTable = ridesTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var ridesTableModel = (RidesTableModel)ridesTable.getModel();
        Ride ride = ridesTableModel.getEntity(ridesTable.getSelectedRow());
        var dialog = new RideDetailDialog(ride);
        dialog.show(ridesTable, "Ride Detail", OK_OPTION, new String[]{"OK"});
    }
}