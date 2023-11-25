package cz.muni.fi.pv168.project.database.entity;

import cz.muni.fi.pv168.project.model.Currency;

import java.time.LocalDateTime;
import java.util.Objects;

public record RideEntity(
        Integer id,
        float distance,
        LocalDateTime dateTime,
        float price,
        Currency originalCurrency,
        int categoryId,
        int passengersCount) {

    public RideEntity(
            Integer id,
            float distance,
            LocalDateTime dateTime,
            float price,
            Currency originalCurrency,
            int categoryId,
            int passengersCount) {
        this.id = id;
        this.distance = distance;
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime cannot be null");
        this.price = price;
        this.originalCurrency = Objects.requireNonNull(originalCurrency, "originalCurrency cannot be null");
        this.categoryId = categoryId;
        this.passengersCount = passengersCount;
    }

    public RideEntity(
            float distance,
            LocalDateTime dateTime,
            float price,
            Currency originalCurrency,
            int categoryId,
            int passengersCount) {
        this(null, distance, dateTime, price, originalCurrency, categoryId, passengersCount);
    }
}