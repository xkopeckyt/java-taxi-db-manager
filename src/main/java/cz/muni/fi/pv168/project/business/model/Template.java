package cz.muni.fi.pv168.project.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Template extends Entity{

    private static final UuidGuidProvider guidProvider = new UuidGuidProvider();
    private static Currency globalCurrency = Currency.EUR;
    private BigDecimal distance;
    private LocalDateTime templateDateTime;
    private BigDecimal price;
    private Currency originalCurrency;
    private Category category;
    private int passengersCount;
    private String name;

    public Template(String name, BigDecimal distance, LocalDateTime templateDateTime,
                    BigDecimal price, Currency originalCurrency, Category category, int passengersCount) {
        super(guidProvider.newGuid());
        setName(name);
        setDistance(distance);
        setTemplateDateTime(templateDateTime);
        setPrice(price);
        setOriginalCurrency(originalCurrency);
        setPassengersCount(passengersCount);
        setCategory(category);
    }

    public Template(String guid, String name, BigDecimal distance, LocalDateTime templateDateTime,
                    BigDecimal price, Currency originalCurrency, Category category, int passengersCount) {
        super(guid);
        setName(name);
        setDistance(distance);
        setTemplateDateTime(templateDateTime);
        setPrice(price);
        setOriginalCurrency(originalCurrency);
        setPassengersCount(passengersCount);
        setCategory(category);
    }

    public static Currency getGlobalCurrency() {
        return globalCurrency;
    }

    public static void setGlobalCurrency(Currency globalCurrency) {
        Template.globalCurrency = globalCurrency;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public LocalDateTime getTemplateDateTime() {
        return templateDateTime;
    }

    public void setTemplateDateTime(LocalDateTime templateDateTime) {
        this.templateDateTime = Objects.requireNonNull(templateDateTime, "dateTime must not be null");;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public String toString() {
        return getName();
    }
}
