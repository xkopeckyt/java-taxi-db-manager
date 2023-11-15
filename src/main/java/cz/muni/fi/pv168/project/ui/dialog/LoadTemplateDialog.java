package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.StringListModel;

import javax.swing.*;
import java.util.Set;

public class LoadTemplateDialog extends EntityDialog<String>{

    private final JComboBox<String> templateNames;

    public LoadTemplateDialog (Set<String> names) {
        this.templateNames = new JComboBox<>( new ComboBoxModelAdapter<>(new StringListModel(names.stream().toList())));
        addFields();
        setValues();
    }

    private void addFields() {
        add("Names", templateNames);
    }

    private void setValues() {
        templateNames.setSelectedIndex(0);
    }

    @Override
    String getEntity() {
        return templateNames.getSelectedItem().toString();
    }
}
