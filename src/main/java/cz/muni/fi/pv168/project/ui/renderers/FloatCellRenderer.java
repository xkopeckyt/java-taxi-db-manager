package cz.muni.fi.pv168.project.ui.renderers;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;

public class FloatCellRenderer extends DefaultTableCellRenderer {
    private final DecimalFormat decFormat;
    public FloatCellRenderer(int decimalPlaces) {
        super();
        this.decFormat = new DecimalFormat("0." + "#".repeat(Math.max(0, decimalPlaces)));
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof Float fl))
            throw new RuntimeException("Wrong Object type, expected: " + Float.class.getName() + ", got: " + value.getClass().getName());
        super.setText(decFormat.format(fl));
    }
}
