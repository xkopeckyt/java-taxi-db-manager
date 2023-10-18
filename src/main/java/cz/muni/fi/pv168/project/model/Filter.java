package cz.muni.fi.pv168.project.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Filter {
    private float distanceFrom;
    private float distanceTo;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private Currency originalCurrency;
    private Category category;

    public Filter(float distanceFrom, float distanceTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo,
                  Currency originalCurrency, Category category) {
        setDistanceFrom(distanceFrom);
        setDistanceTo(distanceTo);
        setDateTimeFrom(dateTimeFrom);
        setDateTimeTo(dateTimeTo);
        setOriginalCurrency(originalCurrency);
        setCategory(category);
    }

    public float getDistanceFrom() {
        return distanceFrom;
    }

    public void setDistanceFrom(float distanceFrom) {
        this.distanceFrom = distanceFrom;
    }

    public float getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(float distanceTo) {
        this.distanceTo = distanceTo;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = Objects.requireNonNull(dateTimeFrom, "dateTimeFrom must not be null");
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = Objects.requireNonNull(dateTimeTo, "dateTimeTo must not be null");
    }

    public Currency getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(Currency originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = Objects.requireNonNull(category, "category must not be null");
    }

    @Override
    public String toString() {
        return "Filter";
    }
}
