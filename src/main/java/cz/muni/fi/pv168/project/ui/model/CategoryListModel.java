package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {
    private final List<Category> categories;

    public CategoryListModel(List<Category> categories) {
        this.categories = new ArrayList<>(categories);
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Category getElementAt(int index) {
        return categories.get(index);
    }

    public void remove(int index){
        categories.remove(index);
        fireIntervalRemoved(this, index, index);
    }

    public void add(Category category){
        categories.add(category);
    }

    public int getIndex(Category category){
       return categories.indexOf(category);
    }

    public void clearCategories() {
        categories.clear();
    }
}

