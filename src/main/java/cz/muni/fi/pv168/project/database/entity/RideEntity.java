package cz.muni.fi.pv168.project.database.entity;

import cz.muni.fi.pv168.project.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record RideEntity(
        Integer id,
        BigDecimal distance,
        LocalDateTime dateTime,
        BigDecimal price,
        Currency originalCurrency,
        int categoryId,
        int passengersCount) {

    public RideEntity(
            Integer id,
            BigDecimal distance,
            LocalDateTime dateTime,
            BigDecimal price,
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
            BigDecimal distance,
            LocalDateTime dateTime,
            BigDecimal price,
            Currency originalCurrency,
            int categoryId,
            int passengersCount) {
        this(null, distance, dateTime, price, originalCurrency, categoryId, passengersCount);
    }
}