package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.export.ExportService;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLOutput;

public class ExportDataAction extends AbstractAction {
    private final Component parent;
    private final ExportService exportService;

    public ExportDataAction(Component parent, ExportService exportService) {
        super("Export Data", Icons.EXPORT_ICON);
        this.parent = parent;
        this.exportService = exportService;
        putValue(SHORT_DESCRIPTION, "Export rides, categories and technical license validity date");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int dialogResult = fileChooser.showSaveDialog(parent);
        if (dialogResult == JFileChooser.APPROVE_OPTION) {
            String exportFile = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(exportFile);

            exportService.exportData(exportFile);

            JOptionPane.showMessageDialog(parent, "Export has successfully finished.");
        }
    }
}
