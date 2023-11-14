package cz.muni.fi.pv168.project.ui.components;

import cz.muni.fi.pv168.project.ui.listeners.AbstractFieldListener;

import javax.swing.*;
import java.awt.*;

public class JStatusTextField extends JTextField {

    private boolean valid = false;

    public JStatusTextField() {
        super();
    }

    public void addFieldListener(AbstractFieldListener fieldListener) {
        getDocument().addDocumentListener(fieldListener);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        if (this.valid) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.decode("#ff4040"));
        }
    }
}
