package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TemplateListModel extends AbstractListModel<Template> {

    private final CrudService<Template> templateCrudService;
    private List<Template> templates;

    public TemplateListModel(CrudService<Template> templateCrudService) {
        this.templates = new ArrayList<>(templateCrudService.findAll());
        this.templateCrudService = templateCrudService;
    }

    @Override
    public int getSize() {
        return templates.size();
    }

    @Override
    public Template getElementAt(int index) {
        return templates.get(index);
    }

    public List<String> getNames() {
        return templates.stream().map(Template::getName).collect(Collectors.toList());
    }

    public void removeRow(int index){
        var categoryToRemove = templates.get(index);
        templateCrudService.deleteByGuid(categoryToRemove.getGuid());
        templates.remove(index);
        fireIntervalRemoved(this, index, index);
    }

    public void addRow(Template template){
        int i = templates.size();
        templateCrudService.create(template)
                .intoException();
        templates.add(template);
        this.fireIntervalAdded(templates, i, i);
    }

    public void updateRow(Template template) {
        templateCrudService.update(template)
                .intoException();
        int rowIndex = templates.indexOf(template);
        this.fireContentsChanged(templates, rowIndex, rowIndex);
    }

    public int getIndex(Template template){
        return templates.indexOf(template);
    }

    public Template getTemplate(String name) {
        return templates.stream().filter(template -> template.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean isNameUsed(String templateName) {
        for (int i = 0; i < getSize(); i++) {
            if (getElementAt(i).getName().equals(templateName)) {
                return true;
            }
        }
        return false;
    }

    public void clearTemplates() {
        templates.clear();
        templateCrudService.deleteAll();
    }

    public void refresh() {
        System.out.println(templateCrudService.findAll().size());
        this.templates = new ArrayList<>(templateCrudService.findAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }
}
