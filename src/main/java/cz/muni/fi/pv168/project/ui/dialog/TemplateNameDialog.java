package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DrivingLicence;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;

import javax.swing.*;
import java.util.List;
import java.util.Optional;


public class TemplateNameDialog extends EntityDialog<String> {

    private JTextField templateNameField;

    public TemplateNameDialog(String name){
        templateNameField = new JTextField(name);
        add("Template name: ", templateNameField);
    }

    public TemplateNameDialog() {
        this("");
    }

    @Override
    String getEntity() {
        return templateNameField.getText();
    }
}
