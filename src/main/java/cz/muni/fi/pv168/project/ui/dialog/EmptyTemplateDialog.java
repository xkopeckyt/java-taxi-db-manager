package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;

public class EmptyTemplateDialog extends EntityDialog<Object>{

    private final JLabel label;

    public EmptyTemplateDialog() {
        this.label = new JLabel("There are no templates to chose from!");

        addFields();
    }

    private void addFields() {
        addLabel(label);
    }

    @Override
    Object getEntity() {
        return null;
    }
}
