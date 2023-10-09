package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewRideFromTemplateAction extends AbstractAction {
    public NewRideFromTemplateAction() {
        super("New Ride from Template");
        putValue(SHORT_DESCRIPTION, "Show Create new ride Dialog with chosen Template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_T);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}
