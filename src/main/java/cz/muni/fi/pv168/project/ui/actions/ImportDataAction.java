package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ImportDataAction extends AbstractAction {
    public ImportDataAction() {
        super("Import Data");
        putValue(SHORT_DESCRIPTION, "Import rides, categories and technical license validity date");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}