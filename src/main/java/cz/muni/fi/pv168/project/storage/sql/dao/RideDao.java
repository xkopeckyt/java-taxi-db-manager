package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.storage.sql.entity.RideEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.Collection;
import java.util.List;

public class RideDao implements DataAccessObject<RideEntity>{
    private final Supplier<ConnectionHandler> connections;
    public RideDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }
    @Override
    public RideEntity create(RideEntity newRide) {
        var sql = "INSERT INTO Ride (id, distance, dateTime, price, originalCurrency, categoryId, " +
                "passengersCount, guid) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, newRide.id());
            statement.setBigDecimal(2, newRide.distance());
            statement.setTimestamp(3, Timestamp.valueOf(newRide.dateTime()));
            statement.setBigDecimal(4, newRide.price());
            statement.setString(5, newRide.originalCurrency().toString());
            statement.setInt(6, newRide.categoryId());
            statement.setInt(7, newRide.passengersCount());
            statement.setString(8, newRide.guid());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long rideId;

                if (keyResultSet.next()) {
                    rideId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newRide);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newRide);
                }

                return findById(rideId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newRide, ex);
        }
    }

    @Override
    public RideEntity update(RideEntity entity) {
        var sql = """
                UPDATE Ride
                SET distance = ?,
                    dateTime = ?,
                    price = ?,
                    originalCurrency = ?,
                    categoryId = ?,
                    passengersCount = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setBigDecimal(1, entity.distance());
            statement.setTimestamp(2, Timestamp.valueOf(entity.dateTime()));
            statement.setBigDecimal(3, entity.price());
            statement.setString(4, entity.originalCurrency().toString());
            statement.setInt(5, entity.categoryId());
            statement.setInt(6, entity.passengersCount());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Ride not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 ride (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update ride: " + entity, ex);
        }
    }

    @Override
    public Collection<RideEntity> findAll() {
        var sql = """
            SELECT id,
                   distance,
                   dateTime,
                   price,
                   originalCurrency,
                   categoryId,
                   passengersCount,
                   guid
            FROM Ride
            """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            List<RideEntity> rides = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var ride = rideFromResultSet(resultSet);
                    rides.add(ride);
                }
            }

            return rides;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all rides", ex);
        }
    }

    @Override
    public Optional<RideEntity> findById(long id) {
        var sql = """
                SELECT id,
                   distance,
                   dateTime,
                   price,
                   originalCurrency,
                   categoryId,
                   passengersCount,
                   guid
                FROM Ride
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(rideFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load ride by id: " + id, ex);
        }
    }

    @Override
    public Optional<RideEntity> findByGuid(String guid) {
        var sql = """
                SELECT id,
                   distance,
                   dateTime,
                   price,
                   originalCurrency,
                   categoryId,
                   passengersCount,
                   guid
                FROM Ride
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(rideFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load ride by guid: " + guid, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = """
                DELETE FROM Ride
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Ride not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 ride (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete ride, guid: " + guid, ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM Ride
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            try (var resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if ride exists, guid: " + guid, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Ride";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all rides", ex);
        }
    }

    private static RideEntity rideFromResultSet(ResultSet resultSet) throws SQLException{
        return new RideEntity(
                resultSet.getInt("id"),
                resultSet.getBigDecimal("distance"),
                resultSet.getTimestamp("dateTime").toLocalDateTime(),
                resultSet.getBigDecimal("price"),
                Currency.valueOf(resultSet.getString("originalCurrency")),
                resultSet.getInt("categoryId"),
                resultSet.getInt("passengersCount"),
                resultSet.getString("guid")
        );
    }
}
