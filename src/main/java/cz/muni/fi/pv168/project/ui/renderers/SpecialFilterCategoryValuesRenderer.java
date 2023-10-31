package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCategoryValues;
import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCurrencyValues;

import javax.swing.*;
import java.awt.*;

public class SpecialFilterCategoryValuesRenderer extends AbstractRenderer<SpecialCategoryValues> {
    public SpecialFilterCategoryValuesRenderer() {
        super(SpecialCategoryValues.class);
    }

    protected void updateLabel(JLabel label, SpecialCategoryValues value) {
        switch (value) {
            case ALL -> renderAll(label);
        }
    }

    private static void renderAll(JLabel label) {
        label.setText("(ALL)");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }
}
