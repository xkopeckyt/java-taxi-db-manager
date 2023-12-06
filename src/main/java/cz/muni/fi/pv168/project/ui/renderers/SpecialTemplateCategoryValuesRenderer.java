package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.ui.filters.Values.TemplateCategoryValues;

import javax.swing.*;
import java.awt.*;

public class SpecialTemplateCategoryValuesRenderer extends AbstractRenderer<TemplateCategoryValues> {
    public SpecialTemplateCategoryValuesRenderer() {
        super(TemplateCategoryValues.class);
    }

    protected void updateLabel(JLabel label, TemplateCategoryValues value) {
        switch (value) {
            case NONE -> renderNone(label);
        }
    }

    private static void renderNone(JLabel label) {
        label.setText("(NONE)");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }

}
