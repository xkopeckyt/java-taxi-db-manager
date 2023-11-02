package cz.muni.fi.pv168.project.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ride {
    private static Currency globalCurrency = Currency.EUR;
    private float distance;
    private LocalDateTime dateTime;
    private float price;
    private Currency originalCurrency;
    private Category category;
    private int passengersCount;

    public Ride(float distance, LocalDateTime dateTime, float price, Currency originalCurrency, Category category, int passengersCount) {
        setDistance(distance);
        setDateTime(dateTime);
        setPrice(price);
        setOriginalCurrency(originalCurrency);
        setCategory(category);
        setPassengersCount(passengersCount);
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime must not be null");
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public int getPassengersCount() {
        return passengersCount;
    }

    public void setPassengersCount(int passengersCount) {
        this.passengersCount = passengersCount;
    }

    public static Currency getGlobalCurrency() {
        return globalCurrency;
    }

    public static void setGlobalCurrency(Currency globalCurrency) {
        Ride.globalCurrency = globalCurrency;
    }

    @Override
    public String toString() {
        return dateTime.toString() + ": " + distance + " km," + price + ' ' + getGlobalCurrency().toString();
    }
}
