package cz.muni.fi.pv168.project.storage.sql.db;

public interface TransactionManager {

    Transaction beginTransaction();

    ConnectionHandler getConnectionHandler();

    boolean hasActiveTransaction();
}
