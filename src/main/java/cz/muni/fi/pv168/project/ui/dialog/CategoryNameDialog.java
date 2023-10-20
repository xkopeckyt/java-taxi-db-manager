package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;

import javax.swing.*;

public class CategoryNameDialog extends EntityDialog<Category> {
    private JTextField categoryNameField;
    private Category category;
    public CategoryNameDialog(Category category){
        this.category = category;
        categoryNameField = new JTextField(category == null ? "" : category.getName());
        add("Category name: ", categoryNameField);
    }

    @Override
    Category getEntity() {
        String categoryName = categoryNameField.getText();
        if (category == null) {
            category = new Category(categoryName);
        }
        category.setName(categoryName);
        return category;
    }
}
