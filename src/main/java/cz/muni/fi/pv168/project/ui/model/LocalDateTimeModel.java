package cz.muni.fi.pv168.project.ui.model;

import org.jdatepicker.DateModel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

//edited implementation of AbstractDateTime from jdatepicker
public class LocalDateTimeModel implements DateModel<LocalDateTime> {

    public static final String PROPERTY_YEAR = "year";
    public static final String PROPERTY_MONTH = "month";
    public static final String PROPERTY_DAY = "day";
    public static final String PROPERTY_VALUE = "value";
    public static final String PROPERTY_SELECTED = "selected";
    private boolean selected = false;
    private Calendar calendarValue = Calendar.getInstance();
    private final Set<ChangeListener> changeListeners = new HashSet<>();
    private final Set<PropertyChangeListener> propertyChangeListeners = new HashSet<>();

    public LocalDateTimeModel() {
    }

    public synchronized void addChangeListener(ChangeListener changeListener) {
        this.changeListeners.add(changeListener);
    }

    public synchronized void removeChangeListener(ChangeListener changeListener) {
        this.changeListeners.remove(changeListener);
    }

    protected synchronized void fireChangeEvent() {

        for (ChangeListener changeListener : this.changeListeners) {
            changeListener.stateChanged(new ChangeEvent(this));
        }

    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeListeners.add(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeListeners.remove(listener);
    }

    protected synchronized void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (oldValue == null || !oldValue.equals(newValue)) {

            for (PropertyChangeListener listener : this.propertyChangeListeners) {
                listener.propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
            }

        }
    }

    public int getDay() {
        return this.calendarValue.get(Calendar.DATE);
    }

    public int getMonth() {
        return this.calendarValue.get(Calendar.MONTH);
    }

    public int getYear() {
        return this.calendarValue.get(Calendar.YEAR);
    }

    public LocalDateTime getValue() {
        return !this.selected ? null : this.fromCalendar(this.calendarValue);
    }

    public void setDay(int day) {
        int oldDayValue = this.calendarValue.get(Calendar.DATE);
        LocalDateTime oldValue = this.getValue();
        this.calendarValue.set(Calendar.DATE, day);
        this.fireChangeEvent();
        this.firePropertyChange("day", oldDayValue, this.calendarValue.get(5));
        this.firePropertyChange("value", oldValue, this.getValue());
    }

    public void addDay(int add) {
        int oldDayValue = this.calendarValue.get(Calendar.DATE);
        LocalDateTime oldValue = this.getValue();
        this.calendarValue.add(Calendar.DATE, add);
        this.fireChangeEvent();
        this.firePropertyChange("day", oldDayValue, this.calendarValue.get(5));
        this.firePropertyChange("value", oldValue, this.getValue());
    }

    public void setMonth(int month) {
        int oldYearValue = this.calendarValue.get(Calendar.YEAR);
        int oldMonthValue = this.calendarValue.get(Calendar.MONTH);
        int oldDayValue = this.calendarValue.get(Calendar.DATE);
        LocalDateTime oldValue = this.getValue();
        Calendar newVal = Calendar.getInstance();
        newVal.set(Calendar.DATE, 1);
        newVal.set(Calendar.MONTH, month);
        newVal.set(Calendar.YEAR, oldYearValue);
        if (newVal.getActualMaximum(Calendar.DATE) <= oldDayValue) {
            newVal.set(Calendar.DATE, newVal.getActualMaximum(Calendar.DATE));
        } else {
            newVal.set(Calendar.DATE, oldDayValue);
        }

        this.calendarValue.set(Calendar.MONTH, newVal.get(Calendar.MONTH));
        this.calendarValue.set(Calendar.DATE, newVal.get(Calendar.DATE));
        this.fireChangeEvent();
        this.firePropertyChange("month", oldMonthValue, this.calendarValue.get(Calendar.MONTH));
        if (this.calendarValue.get(Calendar.DATE) != oldDayValue) {
            this.firePropertyChange("day", oldDayValue, this.calendarValue.get(Calendar.DATE));
        }

        this.firePropertyChange("value", oldValue, this.getValue());
    }

    public void addMonth(int add) {
        int oldMonthValue = this.calendarValue.get(Calendar.MONTH);
        LocalDateTime oldValue = this.getValue();
        this.calendarValue.add(Calendar.MONTH, add);
        this.fireChangeEvent();
        this.firePropertyChange("month", oldMonthValue, this.calendarValue.get(2));
        this.firePropertyChange("value", oldValue, this.getValue());
    }

    public void setYear(int year) {
        int oldYearValue = this.calendarValue.get(Calendar.YEAR);
        int oldMonthValue = this.calendarValue.get(Calendar.MONTH);
        int oldDayValue = this.calendarValue.get(Calendar.DATE);
        LocalDateTime oldValue = this.getValue();
        Calendar newVal = Calendar.getInstance();
        newVal.set(Calendar.DATE, 1);
        newVal.set(Calendar.MONTH, oldMonthValue);
        newVal.set(Calendar.YEAR, year);
        if (newVal.getActualMaximum(Calendar.DATE) <= oldDayValue) {
            newVal.set(Calendar.DATE, newVal.getActualMaximum(Calendar.DATE));
        } else {
            newVal.set(Calendar.DATE, oldDayValue);
        }

        this.calendarValue.set(Calendar.YEAR, newVal.get(Calendar.YEAR));
        this.calendarValue.set(Calendar.DATE, newVal.get(Calendar.DATE));
        this.fireChangeEvent();
        this.firePropertyChange("year", oldYearValue, this.calendarValue.get(1));
        if (this.calendarValue.get(Calendar.DATE) != oldDayValue) {
            this.firePropertyChange("day", oldDayValue, this.calendarValue.get(5));
        }

        this.firePropertyChange("value", oldValue, this.getValue());
    }

    public void addYear(int add) {
        int oldYearValue = this.calendarValue.get(Calendar.YEAR);
        LocalDateTime oldValue = this.getValue();
        this.calendarValue.add(Calendar.YEAR, add);
        this.fireChangeEvent();
        this.firePropertyChange("year", oldYearValue, this.calendarValue.get(1));
        this.firePropertyChange("value", oldValue, this.getValue());
    }

    public void setValue(LocalDateTime value) {
        int oldYearValue = this.calendarValue.get(Calendar.YEAR);
        int oldMonthValue = this.calendarValue.get(Calendar.MONTH);
        int oldDayValue = this.calendarValue.get(Calendar.DATE);
        LocalDateTime oldValue = this.getValue();
        boolean oldSelectedValue = this.isSelected();
        if (value != null) {
            this.calendarValue = this.toCalendar(value);
            //this.setToMidnight();
            this.selected = true;
        } else {
            this.selected = false;
        }

        this.fireChangeEvent();
        this.firePropertyChange("year", oldYearValue, this.calendarValue.get(Calendar.YEAR));
        this.firePropertyChange("month", oldMonthValue, this.calendarValue.get(Calendar.MONTH));
        this.firePropertyChange("day", oldDayValue, this.calendarValue.get(Calendar.DATE));
        this.firePropertyChange("value", oldValue, this.getValue());
        this.firePropertyChange("selected", oldSelectedValue, this.selected);
    }

    public void setDate(int year, int month, int day) {
        int oldYearValue = this.calendarValue.get(Calendar.YEAR);
        int oldMonthValue = this.calendarValue.get(Calendar.MONTH);
        int oldDayValue = this.calendarValue.get(Calendar.DATE);
        LocalDateTime oldValue = this.getValue();
        this.calendarValue.set(year, month, day);
        this.fireChangeEvent();
        this.firePropertyChange("year", oldYearValue, this.calendarValue.get(1));
        this.firePropertyChange("month", oldMonthValue, this.calendarValue.get(2));
        this.firePropertyChange("day", oldDayValue, this.calendarValue.get(5));
        this.firePropertyChange("value", oldValue, this.getValue());
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        LocalDateTime oldValue = this.getValue();
        boolean oldSelectedValue = this.isSelected();
        this.selected = selected;
        this.fireChangeEvent();
        this.firePropertyChange("value", oldValue, this.getValue());
        this.firePropertyChange("selected", oldSelectedValue, this.selected);
    }

    private void setToMidnight() {
        this.calendarValue.set(Calendar.HOUR, 0);
        this.calendarValue.set(Calendar.MINUTE, 0);
        this.calendarValue.set(Calendar.SECOND, 0);
        this.calendarValue.set(Calendar.MILLISECOND, 0);
    }

    protected Calendar toCalendar(LocalDateTime dateTime) {
        return GregorianCalendar.from(dateTime.atZone(ZoneId.systemDefault()));
    }

    protected LocalDateTime fromCalendar(Calendar calendar) {
        return calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
