package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.business.model.Category;

import javax.swing.*;

public final class CategoryRenderer extends AbstractRenderer<Category> {
    //private static final Map<Currency, Icon> CATEGORY_ICONS = Icons.createEnumIcons(Currency.class, 16);

    public CategoryRenderer() {
        super(Category.class);
    }

    @Override
    protected void updateLabel(JLabel label, Category category) {
        if (category != null) {
            //label.setIcon(CURRENCY_ICONS.get(currency));
            label.setText(category.toString());
        }
    }
}
