package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditRideAction extends AbstractAction {
    public EditRideAction() {
        super("Edit Ride", Icons.EDIT_ICON);
        putValue(SHORT_DESCRIPTION, "Show Edit Ride Dialog");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}