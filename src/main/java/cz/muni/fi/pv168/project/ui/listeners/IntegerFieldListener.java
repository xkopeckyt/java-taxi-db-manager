package cz.muni.fi.pv168.project.ui.listeners;

import cz.muni.fi.pv168.project.ui.components.JStatusTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

public class IntegerFieldListener extends AbstractFieldListener {

    public IntegerFieldListener(JStatusTextField textField) {
        super(textField);
    }

    public IntegerFieldListener() {
        super();
    }

    protected void check() {
        //Runnable format = () -> {
            String text = textField.getText();
            boolean good = text.matches("\\d+");
            textField.setValid(good);
        //};
        //SwingUtilities.invokeLater(format);
    }
}
