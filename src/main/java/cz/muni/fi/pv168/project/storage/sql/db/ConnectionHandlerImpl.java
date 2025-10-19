package cz.muni.fi.pv168.project.storage.sql.db;


import cz.muni.fi.pv168.project.storage.DataStorageException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

class ConnectionHandlerImpl implements ConnectionHandler {

    private final Connection connection;

    ConnectionHandlerImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "Missing connection object");
    }

    @Override
    public Connection use() {
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection.getAutoCommit()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DataStorageException("Unable close database connection", e);
        }
    }
}
