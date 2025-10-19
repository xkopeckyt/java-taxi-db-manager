package cz.muni.fi.pv168.project.ui.components;

import cz.muni.fi.pv168.project.ui.listeners.AbstractFieldListener;

import javax.swing.JTextField;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class JStatusTextField extends JTextField {

    private boolean valid = false;
    private final List<BiConsumer<JStatusTextField, Boolean>> onChangeEvent;

    public JStatusTextField() {
        super();
        onChangeEvent = new LinkedList<>();
    }

    public void addOnChangeEvent(BiConsumer<JStatusTextField, Boolean> func) {
        onChangeEvent.add(func);
    }

    public void addOnChangeEvent(Runnable func) {
        onChangeEvent.add((a, b) -> func.run());
    }

    public void addFieldListener(AbstractFieldListener fieldListener) {
        getDocument().addDocumentListener(fieldListener);
        fieldListener.check();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        onChangeEvent.forEach(func -> func.accept(this, valid));
    }
}
