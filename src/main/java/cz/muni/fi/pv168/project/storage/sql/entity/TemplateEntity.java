package cz.muni.fi.pv168.project.storage.sql.entity;

import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.business.model.Ride;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record TemplateEntity(
     Long id,
     String name,
     BigDecimal distance,
     LocalDateTime dateTime,
     BigDecimal price,
     Currency originalCurrency,
     Long categoryId,
     int passengersCount,
     String guid) {

    public TemplateEntity(
            Long id,
            String name,
            BigDecimal distance,
            LocalDateTime dateTime,
            BigDecimal price,
            Currency originalCurrency,
            Long categoryId,
            int passengersCount,
            String guid) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime cannot be null");
        this.price = price;
        this.originalCurrency = Objects.requireNonNull(originalCurrency, "originalCurrency cannot be null");
        this.categoryId = categoryId;
        this.passengersCount = passengersCount;
        this.guid = Objects.requireNonNull(guid, "guid must not be null");
    }

    public TemplateEntity(
            String name,
            BigDecimal distance,
            LocalDateTime dateTime,
            BigDecimal price,
            Currency originalCurrency,
            Long categoryId,
            int passengersCount,
            String guid) {
        this(null, name, distance, dateTime, price, originalCurrency, categoryId, passengersCount, guid);
    }
}
