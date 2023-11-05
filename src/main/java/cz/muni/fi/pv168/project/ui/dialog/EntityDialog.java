package cz.muni.fi.pv168.project.ui.dialog;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

// from https://gitlab.fi.muni.cz/xsokolar/employee-records/-/blob/main/src/main/java/cz/muni/fi/pv168/employees/ui/dialog/EntityDialog.java
abstract class EntityDialog<E> {

    protected final JPanel panel = new JPanel();

    EntityDialog() {
        panel.setLayout(new MigLayout("wrap 2"));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        panel.add(label);
        panel.add(component, "wmin 250lp, grow");
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title, int option, Object[] options) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                option, PLAIN_MESSAGE, null, options, null);
        if (result == OK_OPTION) {
            return Optional.of(getEntity());
        } else {
            return Optional.empty();
        }
    }

    void addButton(JButton button) {
        panel.add(button);
    }

    void addLabel(JLabel label) {
        panel.add(label);
    }

    void addSeparator() {
        panel.add(new JSeparator());
    }
}
