package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ShowRideAction extends AbstractAction {
    public ShowRideAction() {
        super("Show Ride", Icons.SHOW_ICON);
        putValue(SHORT_DESCRIPTION, "Show Ride Info");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}