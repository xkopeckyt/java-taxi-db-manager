package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.business.model.Currency;
import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.TemplateEntity;
import org.tinylog.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class TemplateDao implements DataAccessObject<TemplateEntity>{

    private final Supplier<ConnectionHandler> connections;
    public TemplateDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public TemplateEntity create(TemplateEntity newTemplate) {
        var sql = "INSERT INTO Template (name, distance, dateTime, price, originalCurrency, categoryId, " +
                "passengersCount, guid) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newTemplate.name());
            statement.setBigDecimal(2, newTemplate.distance());
            statement.setTimestamp(3, Timestamp.valueOf(newTemplate.dateTime()));
            statement.setBigDecimal(4, newTemplate.price());
            statement.setString(5, newTemplate.originalCurrency().toString());
            statement.setLong(6, newTemplate.categoryId());
            statement.setInt(7, newTemplate.passengersCount());
            statement.setString(8, newTemplate.guid());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long templateId;

                if (keyResultSet.next()) {
                    templateId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newTemplate);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newTemplate);
                }
                var template = findById(templateId).orElseThrow();
                Logger.debug(template.toString());
                return template;
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newTemplate, ex);
        }
    }

    @Override
    public TemplateEntity update(TemplateEntity entity) {
        var sql = """
                UPDATE Template
                SET name = ?,
                    distance = ?,
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
            statement.setString(1, entity.name());
            statement.setBigDecimal(2, entity.distance());
            statement.setTimestamp(3, Timestamp.valueOf(entity.dateTime()));
            statement.setBigDecimal(4, entity.price());
            statement.setString(5, entity.originalCurrency().toString());
            statement.setLong(6, entity.categoryId());
            statement.setInt(7, entity.passengersCount());
            statement.setLong(8, entity.id());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 template (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update template: " + entity, ex);
        }
    }

    @Override
    public Collection<TemplateEntity> findAll() {
        var sql = """
            SELECT id,
                   name,
                   distance,
                   dateTime,
                   price,
                   originalCurrency,
                   categoryId,
                   passengersCount,
                   guid
            FROM Template
            """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            List<TemplateEntity> templates = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var template = templateFromResultSet(resultSet);
                    templates.add(template);
                }
            }

            return templates;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all templates", ex);
        }
    }

    @Override
    public Optional<TemplateEntity> findById(long id) {
        var sql = """
                SELECT id,
                   name,                
                   distance,
                   dateTime,
                   price,
                   originalCurrency,
                   categoryId,
                   passengersCount,
                   guid
                FROM Template
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(templateFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load template by id: " + id, ex);
        }
    }

    @Override
    public Optional<TemplateEntity> findByGuid(String guid) {
        var sql = """
                SELECT id,
                   name,
                   distance,
                   dateTime,
                   price,
                   originalCurrency,
                   categoryId,
                   passengersCount,
                   guid
                FROM Template
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(templateFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load template by guid: " + guid, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = """
                DELETE FROM Template
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 template (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete template, guid: " + guid, ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM Template
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
            throw new DataStorageException("Failed to check if template exists, guid: " + guid, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Template";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all templates", ex);
        }
    }

    private static TemplateEntity templateFromResultSet(ResultSet resultSet) throws SQLException{
        return new TemplateEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getBigDecimal("distance"),
                resultSet.getTimestamp("dateTime").toLocalDateTime(),
                resultSet.getBigDecimal("price"),
                Currency.valueOf(resultSet.getString("originalCurrency")),
                resultSet.getLong("categoryId"),
                resultSet.getInt("passengersCount"),
                resultSet.getString("guid")
        );
    }
}
