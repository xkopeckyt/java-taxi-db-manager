package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteRideAction extends AbstractAction {
    public DeleteRideAction() {
        super("Delete Rides", Icons.DELETE_ICON);
        putValue(SHORT_DESCRIPTION, "Deletes selected rides");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}
