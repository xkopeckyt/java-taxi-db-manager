package cz.muni.fi.pv168.project.model;

import java.time.LocalDateTime;

public class DrivingLicence {

    private LocalDateTime to;
    private boolean valid;

    public DrivingLicence(LocalDateTime to) {
        setTo(to);
        valid = checkLicence();
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean getValid() {
        return this.valid;
    }

    public boolean checkLicence() {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(this.getTo().plusDays(1));
    }

    public boolean checkDate(LocalDateTime date) {
        return date.isBefore(to.plusDays(1));
    }
}
