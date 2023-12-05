package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {
    private final CrudService<Category> categoryCrudService;
    private List<Category> categories;

    public CategoryListModel(CrudService<Category> categoryCrudService) {
        this.categories = new ArrayList<>(categoryCrudService.findAll());
        this.categoryCrudService = categoryCrudService;
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Category getElementAt(int index) {
        return categories.get(index);
    }

    public void removeRow(int index){
        var categoryToRemove = categories.get(index);
        categoryCrudService.deleteByGuid(categoryToRemove.getGuid());
        categories.remove(index);
        fireIntervalRemoved(this, index, index);;
    }

    public void addRow(Category category){
        int i = categories.size();
        categoryCrudService.create(category)
                .intoException();
        categories.add(category);
        this.fireIntervalAdded(categories, i, i);
    }

    public void updateRow(Category category) {
        categoryCrudService.update(category)
                .intoException();
        int rowIndex = categories.indexOf(category);
        this.fireContentsChanged(categories, rowIndex, rowIndex);
    }

    public int getIndex(Category category){
       return categories.indexOf(category);
    }

    public boolean isNameUsed(String categoryName) {
        for (int i = 0; i < getSize(); i++) {
            if (getElementAt(i).getName().equals(categoryName)) {
                return true;
            }
        }
        return false;
    }

    public void clearCategories() {
        categories.clear();
        categoryCrudService.deleteAll();
    }

    public void refresh() {
        this.categories = new ArrayList<>(categoryCrudService.findAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }
}

