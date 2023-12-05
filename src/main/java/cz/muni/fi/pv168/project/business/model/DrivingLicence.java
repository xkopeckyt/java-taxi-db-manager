package cz.muni.fi.pv168.project.business.model;

import java.time.LocalDate;

public class DrivingLicence {

    private LocalDate to;

    public DrivingLicence(LocalDate to) {
        setTo(to);
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public boolean isValid() {
        LocalDate now = LocalDate.now();
        return now.isBefore(to.plusDays(1));
    }

    public boolean checkDate(LocalDate date) {
        if (date == null) return false;
        return date.isBefore(to.plusDays(1));
    }
}
