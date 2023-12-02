package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RideEntity;
import cz.muni.fi.pv168.project.model.Ride;

public class RideMapper {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final CategoryMapper categoryMapper;

    public RideMapper(
            DataAccessObject<CategoryEntity> categoryDao,
            CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    public Ride mapToBusiness(RideEntity entity) {
        var category = categoryDao
                .findById(entity.id())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new RuntimeException("Category not found: " +
                        entity.categoryId()));

        return new Ride(
                entity.distance(),
                entity.dateTime(),
                entity.price(),
                entity.originalCurrency(),
                category,
                entity.passengersCount(),
                entity.guid()
        );
    }

    public RideEntity mapNewRideToDatabase(Ride ride) {
        CategoryEntity categoryEntity = categoryDao
                .findByGuid(ride.getCategory().getGuid())
                .orElseThrow(() -> new RuntimeException("Category not found with name: " +
                        ride.getCategory().getName()));

        return new RideEntity(
                ride.getDistance(),
                ride.getDateTime(),
                ride.getPrice(),
                ride.getOriginalCurrency(),
                categoryEntity.id(),
                ride.getPassengersCount(),
                ride.getGuid()
        );
    }

    public RideEntity mapExistingRideToDatabase(Ride ride, Integer id) {
        CategoryEntity categoryEntity = categoryDao
                .findByGuid(ride.getCategory().getGuid())
                .orElseThrow(() -> new RuntimeException("Category not found with name: " +
                        ride.getCategory().getName()));

        return new RideEntity(
                id,
                ride.getDistance(),
                ride.getDateTime(),
                ride.getPrice(),
                ride.getOriginalCurrency(),
                categoryEntity.id(),
                ride.getPassengersCount(),
                ride.getGuid()
        );
    }
}

