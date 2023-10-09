package cz.muni.fi.pv168.project.ui;

import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.DateModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LocalDateTimeModel extends AbstractDateModel<LocalDateTime> implements DateModel<LocalDateTime> {

    @Override
    protected Calendar toCalendar(LocalDateTime dateTime) {
        return GregorianCalendar.from(dateTime.atZone(ZoneId.systemDefault()));
    }

    @Override
    protected LocalDateTime fromCalendar(Calendar calendar) {
        return calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
