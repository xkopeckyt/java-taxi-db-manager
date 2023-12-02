package cz.muni.fi.pv168.project.storage.sql.entity;

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
        int passengersCount,
        String guid) {

    public RideEntity(
            Integer id,
            float distance,
            LocalDateTime dateTime,
            float price,
            Currency originalCurrency,
            int categoryId,
            int passengersCount,
            String guid) {
        this.id = id;
        this.distance = distance;
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime cannot be null");
        this.price = price;
        this.originalCurrency = Objects.requireNonNull(originalCurrency, "originalCurrency cannot be null");
        this.categoryId = categoryId;
        this.passengersCount = passengersCount;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
    }

    public RideEntity(
            float distance,
            LocalDateTime dateTime,
            float price,
            Currency originalCurrency,
            int categoryId,
            int passengersCount,
            String guid) {
        this(null, distance, dateTime, price, originalCurrency, categoryId, passengersCount, guid);
    }
}