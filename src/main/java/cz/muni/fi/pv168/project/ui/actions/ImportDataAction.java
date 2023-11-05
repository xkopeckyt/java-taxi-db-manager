package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ImportDataAction extends AbstractAction {
    public ImportDataAction() {
        super("Import Data", Icons.IMPORT_ICON);
        putValue(SHORT_DESCRIPTION, "Import rides, categories and technical license validity date");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}