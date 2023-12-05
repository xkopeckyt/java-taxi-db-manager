package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Ride;

import javax.swing.*;


import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.util.Map;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;


public class SaveTemplateDialog extends EntityDialog<String> {

    private final JTextField templateName;
    private final JLabel label;
    private final JButton okButton;
    private final Map<String, Ride> templates;

    public SaveTemplateDialog(Map<String, Ride> templates, JButton okButton) {
        this.templates = templates;
        this.templateName = new JTextField();
        this.label = new JLabel("");
        this.okButton = okButton;

        label.setForeground(Color.RED);

        templateName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDialog();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               updateDialog();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateDialog();
            }
        });
        addFields();
        templateName.setText("");
        templateName.setBackground(Color.RED);
        okButton.setEnabled(false);
    }

    private void addFields() {
        add("Template name:",templateName);
        addLabel(label);
    }

    private void updateDialog() {
        if (templateName.getText().isEmpty()) {
            templateName.setBackground(Color.RED);
            okButton.setEnabled(false);
            label.setText("");
        } else if (templates.containsKey(templateName.getText())) {
            okButton.setEnabled(false);
            label.setText("Name used!");
            templateName.setBackground(Color.WHITE);
        } else {
            okButton.setEnabled(true);
            label.setText("");
            templateName.setBackground(Color.WHITE);
        }
    }

    public static Optional<String> showDialog(Map<String, Ride> templates) {
        var okButton = DialogUtils.createButton("Ok");
        var dialog = new SaveTemplateDialog(templates, okButton);
        return dialog.show(null, "Save template", OK_CANCEL_OPTION, new Object[]{ okButton, "Cancel"});
    }

    @Override
    String getEntity() {
        return templateName.getText();
    }
}
