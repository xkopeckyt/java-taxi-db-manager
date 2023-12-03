package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.export.ImportService;
import cz.muni.fi.pv168.project.ui.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.ui.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.ui.export.format.FormatMapping;
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
            importMessage = "Import was done";
            try {
                importService.importData(importFile.getAbsolutePath());
            } catch (BatchOperationException ex) {
                importMessage = "Import was unsuccessful because wrong file format was chosen.";
            } catch (ArrayIndexOutOfBoundsException ex) {
                importMessage = "Data in .csv file are not correctly formatted";
            } catch (Exception ex) {
                importMessage = "Data was not imported due to unknown error";
            }
        } else {
            importMessage = "Import was cancelled";
        }
        JOptionPane.showMessageDialog(parent, importMessage);
    }
}