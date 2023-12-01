package cz.muni.fi.pv168.project.ui.listeners;

import cz.muni.fi.pv168.project.ui.components.JStatusTextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class AbstractFieldListener implements DocumentListener {

    protected JStatusTextField textField;

    public AbstractFieldListener(JStatusTextField textField) {
        super();
        this.textField = textField;
    }

    public AbstractFieldListener() {
        super();
    }

    public void setStatusTextField(JStatusTextField textField) {
        this.textField = textField;
    }

    protected abstract void check();

    @Override
    public void insertUpdate(DocumentEvent e) {
        check();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        check();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
