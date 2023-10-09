package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SetFilterAction extends AbstractAction {
    public SetFilterAction() {
        super("Set Filter");
        putValue(SHORT_DESCRIPTION, "Show Set filter Dialog for rides");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            System.out.println(this.getClass().getName());
        }
}