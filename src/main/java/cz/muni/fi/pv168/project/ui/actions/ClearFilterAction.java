package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ClearFilterAction extends AbstractAction {
    public ClearFilterAction() {
        super("Clear Filter");
        putValue(SHORT_DESCRIPTION, "Clears filter for rides");
        putValue(MNEMONIC_KEY, KeyEvent.VK_C);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}
