package cz.muni.fi.pv168.project.ui.model;

import org.jdatepicker.AbstractDateModel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class LocalDateModel extends AbstractDateModel<LocalDate> {
    @Override
    protected Calendar toCalendar(LocalDate dateTime) {
        return GregorianCalendar.from(dateTime.atStartOfDay(ZoneId.systemDefault()));
    }

    @Override
    protected LocalDate fromCalendar(Calendar calendar) {
        return calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
