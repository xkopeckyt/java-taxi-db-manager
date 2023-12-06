package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;

public class EditCategoriesAction extends AbstractAction {
    private final CategoryListModel categoryListModel;
    private final JTable ridesTable;
    private final Runnable callback;
    public EditCategoriesAction(CategoryListModel categoryListModel, JTable ridesTable, Runnable callback) {
        super("Edit Categories", Icons.SELECT_ICON);
        putValue(SHORT_DESCRIPTION, "Add, rename, delete Categories");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        this.categoryListModel = categoryListModel;
        this.ridesTable = ridesTable;
        this.callback = callback;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new CategoryDialog(categoryListModel, ridesTable, callback);
        var model = new CategoryTableModel(categoryListModel);
        dialog.show(new JTable(model, model.getColumnModel()), "Edit Categories", OK_CANCEL_OPTION, null);
    }
}
