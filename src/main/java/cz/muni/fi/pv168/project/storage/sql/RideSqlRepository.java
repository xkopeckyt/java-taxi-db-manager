package cz.muni.fi.pv168.project.storage.sql;


import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.storage.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.entity.RideEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link Repository} for {@link Ride} entity using SQL database.
 *
 * @author Vojtech Sassmann
 */
public class RideSqlRepository implements Repository<Ride> {

    private final DataAccessObject<RideEntity> rideDao;
    private final EntityMapper<RideEntity, Ride> rideMapper;

    public RideSqlRepository(
            DataAccessObject<RideEntity> rideDao,
            EntityMapper<RideEntity, Ride> rideMapper) {
        this.rideDao = rideDao;
        this.rideMapper = rideMapper;
    }


    @Override
    public List<Ride> findAll() {
        return rideDao
                .findAll()
                .stream()
                .map(rideMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Ride newEntity) {
        rideDao.create(rideMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Ride entity) {
        var existingEmployee = rideDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Employee not found, guid: " + entity.getGuid()));
        var updatedEmployeeEntity = rideMapper
                .mapExistingEntityToDatabase(entity, existingEmployee.id());

        rideDao.update(updatedEmployeeEntity);
    }

    @Override
    public void deleteByGuid(String guid) {
        rideDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        rideDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return rideDao.existsByGuid(guid);
    }

    @Override
    public Optional<Ride> findByGuid(String guid) {
        return rideDao
                .findByGuid(guid)
                .map(rideMapper::mapToBusiness);
    }
}
