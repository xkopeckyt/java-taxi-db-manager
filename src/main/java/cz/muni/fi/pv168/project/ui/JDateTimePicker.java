package cz.muni.fi.pv168.project.ui;
import cz.muni.fi.pv168.project.ui.model.LocalDateTimeModel;
import org.jdatepicker.DateModel;
import org.jdesktop.swingx.calendar.SingleDaySelectionModel;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.awt.*;

public class JDateTimePicker extends JXDatePicker {
    private JSpinner timeSpinner;
    private JPanel timePanel;
    private DateFormat timeFormat;
    private final DateModel<LocalDateTime> model;

    public JDateTimePicker() {
        this(new LocalDateTimeModel());
    }

    public JDateTimePicker(DateModel<LocalDateTime> model) {
        super();
        this.model = model;
        getMonthView().setSelectionModel(new SingleDaySelectionModel());
        setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
        setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
        setLocalDateTime(model.getValue());
    }

    public void setLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            setDate(null);
            return;
        }
        setDate(java.sql.Timestamp.valueOf(dateTime));
    }

    public LocalDateTime getLocalDateTime() {
        if (getDate() == null) return null;
        return getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void commitEdit() {
        commitTime();
        try {
            super.commitEdit();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        model.setValue(getLocalDateTime());
    }

    public void cancelEdit() {
        super.cancelEdit();
        setTimeSpinners();
    }

    @Override
    public JPanel getLinkPanel() {
        super.getLinkPanel();
        if( timePanel == null ) {
            timePanel = createTimePanel();
        }
        setTimeSpinners();
        return timePanel;
    }

    private JPanel createTimePanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());
        //newPanel.add(panelOriginal);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(dateModel);
        if( timeFormat == null ) timeFormat = DateFormat.getTimeInstance( DateFormat.SHORT );
        updateTextFieldFormat();
        newPanel.add(new JLabel( "Time:" ) );
        newPanel.add(timeSpinner);
        newPanel.setBackground(Color.WHITE);
        newPanel.add(createResetButton());
        return newPanel;
    }

    private JButton createResetButton() {
        JButton button = new JButton("Reset");
        button.addActionListener(e -> resetPicker());
        return button;
    }

    private void resetPicker() {
        cancelEdit();
        setLightWeightPopupEnabled(false);
        setLightWeightPopupEnabled(true);
        setDate(null);
        model.setValue(null);
    }

    private void updateTextFieldFormat() {
        if( timeSpinner == null ) return;
        JFormattedTextField tf = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
        DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
        DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();
        // Change the date format to only show the hours
        formatter.setFormat( timeFormat );
    }

    private void commitTime() {
        Date date = getDate();
        if (date != null) {
            Date time = (Date) timeSpinner.getValue();
            GregorianCalendar timeCalendar = new GregorianCalendar();
            timeCalendar.setTime( time );

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get( Calendar.HOUR_OF_DAY ) );
            calendar.set(Calendar.MINUTE, timeCalendar.get( Calendar.MINUTE ) );
            calendar.set(Calendar.SECOND, timeCalendar.get( Calendar.SECOND ));
            calendar.set(Calendar.MILLISECOND, 0);

            Date newDate = calendar.getTime();
            setDate(newDate);
        }

    }

    private void setTimeSpinners() {
        Date date = getDate();
        if (date == null) {
            date = new Date();
        }
        timeSpinner.setValue( date );
    }

    public DateFormat getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(DateFormat timeFormat) {
        this.timeFormat = timeFormat;
        updateTextFieldFormat();
    }

    public static void main(String[] args) {
        Date date = new Date();
        JFrame frame = new JFrame();
        frame.setTitle("Date Time Picker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JDateTimePicker dateTimePicker = new JDateTimePicker();

        //dateTimePicker.setDate(date);

        frame.getContentPane().add(dateTimePicker);
        frame.pack();
        frame.setVisible(true);
    }
}