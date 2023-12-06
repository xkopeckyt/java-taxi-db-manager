package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DrivingLicence;
import cz.muni.fi.pv168.project.ui.dialog.TemplatesDialog;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

public class EditTemplatesAction extends AbstractAction {

    private final TemplateListModel templates;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;

    public EditTemplatesAction(TemplateListModel templates, ListModel<Category> categoryListModel, DrivingLicence licence) {
        super("Edit templates", Icons.TEMPLATES_ICON);
        putValue(SHORT_DESCRIPTION, "Add, rename, delete Templates");
        putValue(MNEMONIC_KEY, KeyEvent.VK_T);
        this.templates = templates;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new TemplatesDialog(templates, categoryListModel, licence);
        var model = new TemplateTableModel(templates);
        dialog.show(new JTable(model, model.getColumnModel()), "Edit Templates", OK_CANCEL_OPTION, null);
    }
}
