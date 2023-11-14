package cz.muni.fi.pv168.project.ui.listeners;

import cz.muni.fi.pv168.project.ui.components.JStatusTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class DecimalFieldListener extends AbstractFieldListener {

    public DecimalFieldListener(JStatusTextField textField) {
        super(textField);
    }

    public DecimalFieldListener() {
        super();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        System.out.println(e.getOffset() + " " + e.getLength());
        lol();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        System.out.println(e.getOffset() + " " + e.getLength());
        lol();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}

    private void lol() {
        Runnable format = () -> {
            String text = textField.getText();
            boolean good = text.matches("\\d+(\\.\\d{0,2})?");
            textField.setValid(good);
        };
        SwingUtilities.invokeLater(format);
    }
}
