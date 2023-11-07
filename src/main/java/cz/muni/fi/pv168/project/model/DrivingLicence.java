package cz.muni.fi.pv168.project.model;

import java.time.LocalDate;

public class DrivingLicence {

    private LocalDate to;
    private boolean valid;

    public DrivingLicence(LocalDate to) {
        setTo(to);
        valid = checkLicence();
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean getValid() {
        return this.valid;
    }

    public boolean checkLicence() {
        LocalDate now = LocalDate.now();
        return now.isBefore(this.getTo().plusDays(1));
    }

    public boolean checkDate(LocalDate date) {
        if (date == null) return false;
        return date.isBefore(to.plusDays(1));
    }
}
