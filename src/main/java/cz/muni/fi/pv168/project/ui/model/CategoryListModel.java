package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {
    private List<Category> categories;

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
        int i = categories.size();
        categories.add(category);
        this.fireIntervalAdded(categories, i, i);
    }

    public int getIndex(Category category){
       return categories.indexOf(category);
    }

    public boolean isNameUsed(String categoryName){
        for(int i = 0; i < getSize(); i++){
            if(getElementAt(i).getName().equals(categoryName)){
                return true;
            }
        }
        return false;
    }
}

