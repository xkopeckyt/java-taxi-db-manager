package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.ui.filters.Values.SpecialCurrencyValues;

import javax.swing.*;
import java.awt.*;

public class SpecialFilterCurrencyValuesRenderer extends AbstractRenderer<SpecialCurrencyValues> {

    public SpecialFilterCurrencyValuesRenderer() {
        super(SpecialCurrencyValues.class);
    }

    protected void updateLabel(JLabel label, SpecialCurrencyValues value) {
        switch (value) {
            case ALL -> renderBoth(label);
        }
    }

    private static void renderBoth(JLabel label) {
        label.setText("(BOTH)");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }
}
