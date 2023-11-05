package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.util.Map;

public final class CurrencyRenderer extends AbstractRenderer<Currency> {

    //private static final Map<Currency, Icon> CURRENCY_ICONS = Icons.createEnumIcons(Currency.class, 16);

    public CurrencyRenderer() {
        super(Currency.class);
    }

    @Override
    protected void updateLabel(JLabel label, Currency currency) {
        if (currency != null) {
            //label.setIcon(CURRENCY_ICONS.get(currency));
            label.setText(currency.toString());
        }
    }
}
