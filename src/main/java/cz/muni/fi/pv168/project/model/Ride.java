package cz.muni.fi.pv168.project.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ride extends Entity{
    private static final UuidGuidProvider guidProvider = new UuidGuidProvider();
    private static Currency globalCurrency = Currency.EUR;
    private BigDecimal distance;
    private LocalDateTime rideDateTime;
    private BigDecimal price;
    private Currency originalCurrency;
    private Category category;
    private int passengersCount;

    public Ride(BigDecimal distance, LocalDateTime rideDateTime, BigDecimal price,
                Currency originalCurrency, Category category, int passengersCount) {
        super(guidProvider.newGuid());
        setDistance(distance);
        setRideDateTime(rideDateTime);
        setPrice(price);
        setOriginalCurrency(originalCurrency);
        setCategory(category);
        setPassengersCount(passengersCount);
    }
    public Ride(BigDecimal distance, LocalDateTime rideDateTime, BigDecimal price,
                Currency originalCurrency, Category category, int passengersCount,
                String guid) {
        super(guid);
        setDistance(distance);
        setRideDateTime(rideDateTime);
        setPrice(price);
        setOriginalCurrency(originalCurrency);
        setCategory(category);
        setPassengersCount(passengersCount);
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public LocalDateTime getRideDateTime() {
        return rideDateTime;
    }

    public void setRideDateTime(LocalDateTime rideDateTime) {
        this.rideDateTime = Objects.requireNonNull(rideDateTime, "dateTime must not be null");
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
        return rideDateTime.toString() + ": " + distance + " km," + price + ' ' + getGlobalCurrency().toString();
    }
}
