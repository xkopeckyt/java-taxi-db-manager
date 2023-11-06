package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;

import javax.swing.*;

public class CategoryNameDialog extends EntityDialog<Category> {
    private JTextField categoryNameField;
    private CategoryListModel categoryListModel;
    private Category category;
    public CategoryNameDialog(Category category, CategoryListModel categoryListModel){
        this.category = category;
        this.categoryListModel = categoryListModel;
        categoryNameField = new JTextField(category == null ? "" : category.getName());
        add("Category name: ", categoryNameField);
    }

    @Override
    Category getEntity() {
        String categoryName = categoryNameField.getText();
        if (category == null) {
            category = new Category(categoryName);
        } else{
            for(int i = 0; i < categoryListModel.getSize(); i++){
                if(!categoryListModel.getElementAt(i).getName().equals(category.getName())
                && categoryListModel.getElementAt(i).getName().equals(categoryName)){
                    JOptionPane.showMessageDialog(null,
                            "The category name: \"" + categoryName + "\" is already used.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return category;
                }
            }
        }
        category.setName(categoryName);
        return category;
    }
}
