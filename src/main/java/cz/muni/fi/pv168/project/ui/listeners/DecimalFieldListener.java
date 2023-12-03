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

    public void check() {
        String text = textField.getText();
        boolean good = text.matches("\\d+([.,]\\d{1,2})?");
        textField.setValid(good);
    }
}
