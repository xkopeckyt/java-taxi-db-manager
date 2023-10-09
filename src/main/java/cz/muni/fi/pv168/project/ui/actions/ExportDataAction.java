package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ExportDataAction extends AbstractAction {
    public ExportDataAction() {
        super("Export Data");
        putValue(SHORT_DESCRIPTION, "Export rides, categories and technical license validity date");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}
