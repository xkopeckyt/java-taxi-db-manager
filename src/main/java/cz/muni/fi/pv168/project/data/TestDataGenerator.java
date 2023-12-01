package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.temporal.ChronoUnit.DAYS;

public class TestDataGenerator {
    private static final UuidGuidProvider provider = new UuidGuidProvider();
    private static final Random random = new Random(2L);
    private static final LocalDate MIN_DATE = LocalDate.of(2020, JANUARY, 1);
    private static final LocalDate MAX_DATE = LocalDate.now(); //LocalDate.of(2025, DECEMBER, 31);
    private static final int MAX_DISTANCE = 200;
    private static final int MAX_PRICE = 200;
    private static final int MAX_PASSENGERS = 15;
    private static final List<Category> CATEGORIES = List.of(
            new Category("Cash", provider.newGuid()),
            new Category("Card", provider.newGuid()),
            new Category("Internal", provider.newGuid()),
            new Category("Out of state", provider.newGuid()),
            new Category("Cash + Internal", provider.newGuid()),
            new Category("Card + Internal", provider.newGuid()),
            new Category("Cash + Out of state", provider.newGuid()),
            new Category("Card + Out of state", provider.newGuid())
    );

    public Ride createTestRide() {
        Currency currency = selectRandom(Arrays.stream(Currency.values()).toList());
        float distance = random.nextFloat(MAX_DISTANCE+1);
        float price = random.nextFloat(MAX_PRICE+1);
        int passengersCount = random.nextInt(1, MAX_PASSENGERS+1);
        LocalDateTime dateTime = selectRandomLocalDateTime(MIN_DATE, MAX_DATE);
        Category category = selectRandom(CATEGORIES);
        return new Ride(distance, dateTime, price, currency, category, passengersCount, provider.newGuid());
    }

    public List<Ride> createTestRides(int count) {
        return Stream
                .generate(this::createTestRide)
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Category> getCategories() {
        return CATEGORIES;
    }

    public DrivingLicence createTestDrivingLicence() {
        return new DrivingLicence(MAX_DATE);
    }

    private <T> T selectRandom(List<T> data) {
        int index = random.nextInt(data.size());
        return data.get(index);
    }

    private LocalDateTime selectRandomLocalDateTime(LocalDate min, LocalDate max) {
        long maxDays = Math.toIntExact(DAYS.between(min, max) + 1);
        long days = random.nextLong(maxDays);
        int hours = random.nextInt(24);
        int minutes = random.nextInt(60);
        int seconds = random.nextInt(60);
        return LocalDateTime.of(min.plusDays(days), LocalTime.of(hours, minutes, seconds));
    }
}
