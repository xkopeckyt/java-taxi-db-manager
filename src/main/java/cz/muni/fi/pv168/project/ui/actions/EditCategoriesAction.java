package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditCategoriesAction extends AbstractAction {
    private CategoryListModel categoryListModel;
    private JTable ridesTable;
    public EditCategoriesAction(CategoryListModel categoryListModel, JTable ridesTable) {
        super("Edit Categories");
        putValue(SHORT_DESCRIPTION, "Add, rename, delete Categories");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        this.categoryListModel = categoryListModel;
        this.ridesTable = ridesTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new CategoryDialog(categoryListModel, ridesTable);
        var model = new CategoryTableModel(categoryListModel);
        dialog.show(new JTable(model, model.getColumnModel()), "Edit Categories");
    }
}
