package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RideDetailAction extends AbstractAction {
    public RideDetailAction() {
        super("Ride detail");
        putValue(SHORT_DESCRIPTION, "Show detail of a ride");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}