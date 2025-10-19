package cz.muni.fi.pv168.project.ui.renderers;

import javax.swing.table.DefaultTableCellRenderer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FloatCellRenderer extends DefaultTableCellRenderer {
    private final int decimalPlaces;
    public FloatCellRenderer(int decimalPlaces) {
        super();
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    public void setValue(Object value) {
        BigDecimal val;
        if (value instanceof Float f) {
            val = BigDecimal.valueOf(Double.parseDouble(Float.toString(f)));
        } else if (value instanceof BigDecimal bd) {
            val = bd;
        } else {
            throw new RuntimeException("Wrong Object type, expected: " + Float.class.getName() +
                    " or " + BigDecimal.class.getName() + ", got: " + value.getClass().getName());
        }
        super.setText(val.setScale(decimalPlaces, RoundingMode.HALF_UP).toString());
    }
}
