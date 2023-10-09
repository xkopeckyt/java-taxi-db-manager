package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditTechnicalLicenceAction extends AbstractAction {
    public EditTechnicalLicenceAction() {
        super("Show/Edit Technical Licence");
        putValue(SHORT_DESCRIPTION, "Show/Edit Technical Licence validity date");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}
