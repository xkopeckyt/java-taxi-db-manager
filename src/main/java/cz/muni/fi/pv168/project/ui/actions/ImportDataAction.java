package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class ImportDataAction extends AbstractAction {
    private final RidesTableModel ridesTableModel;
    private final ImportService importService;
    private final Component parent;


    public ImportDataAction(RidesTableModel ridesTableModel, ImportService importService, Component parent) {
        super("Import Data", Icons.IMPORT_ICON);
        this.ridesTableModel = ridesTableModel;
        this.importService = importService;
        this.parent = parent;
        putValue(SHORT_DESCRIPTION, "Import rides");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int dialogResult = fileChooser.showOpenDialog(parent);
        String importMessage;
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            importService.importData(importFile.getAbsolutePath());
            importMessage = "Import was done";
        } else {
            importMessage = "Could not import that file";
        }
        JOptionPane.showMessageDialog(parent, importMessage);
    }
}