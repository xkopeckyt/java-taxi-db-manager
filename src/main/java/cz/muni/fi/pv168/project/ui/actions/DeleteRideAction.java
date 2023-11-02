package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteRideAction extends AbstractAction {

    private final JTable ridesTable;
    public DeleteRideAction(JTable ridesTable) {
        super("Delete Rides", Icons.DELETE_ICON);
        this.ridesTable = ridesTable;
        putValue(SHORT_DESCRIPTION, "Deletes selected rides");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var ridesTableModel = (RidesTableModel) ridesTable.getModel();
        Arrays.stream(ridesTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(ridesTable::convertRowIndexToModel)
                .boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder())
                .forEach(ridesTableModel::deleteRow);
    }
}
