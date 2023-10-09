package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditCategories extends AbstractAction {
    public EditCategories() {
        super("Edit Categories");
        putValue(SHORT_DESCRIPTION, "Add, rename, delete Categories");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(this.getClass().getName());
    }
}
