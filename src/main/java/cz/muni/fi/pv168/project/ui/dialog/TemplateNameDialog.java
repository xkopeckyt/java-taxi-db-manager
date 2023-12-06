package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DrivingLicence;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;

import javax.swing.*;
import java.util.List;


public class TemplateNameDialog extends EntityDialog<Template> {

    private JTextField templateNameField;
    private TemplateListModel templateListModel;
    private Template template;
    private ListModel<Category> categoryListModel;
    private DrivingLicence licence;
    public TemplateNameDialog(Template template, TemplateListModel templateListModel, ListModel<Category> categoryListModel,
                              DrivingLicence licence){
        this.template = template;
        this.templateListModel = templateListModel;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
        templateNameField = new JTextField(template == null ? "" : template.getName());
        add("Template name: ", templateNameField);
    }

    @Override
    Template getEntity() {
        String templateName = templateNameField.getText();
        if (template == null) {
            var result = RideDialog.showDialog("Add template", null, categoryListModel, licence, templateListModel, false);
            if (result.isPresent()) {
                var ride = result.get();
                template = new Template(templateName, ride.getDistance(), ride.getRideDateTime(), ride.getPrice(),
                        ride.getOriginalCurrency(), ride.getCategory(), ride.getPassengersCount());
            }
        } else{
            for(int i = 0; i < templateListModel.getSize(); i++){
                if(!templateListModel.getElementAt(i).getName().equals(template.getName())
                        && templateListModel.getElementAt(i).getName().equals(templateName)){
                    JOptionPane.showMessageDialog(null,
                            "The template name: \"" + templateName + "\" is already used.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return template;
                }
            }
        }
        template.setName(templateName);
        return template;
    }
}
