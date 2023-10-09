package cz.muni.fi.pv168.project.ui.renderers;

import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeCellRenderer extends DefaultTableCellRenderer {
    public DateTimeCellRenderer() { super(); }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof LocalDateTime dateTime))
            throw new RuntimeException("Wrong Object type, expected: " + LocalDateTime.class.getName() + ", got: " + value.getClass().getName());
        super.setText(dateTime.format(DateTimeFormatter.ofPattern("dd. MM. yyyy HH:mm:ss")));
    }
}
