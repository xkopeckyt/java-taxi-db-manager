package cz.muni.fi.pv168.project.storage.sql.db;

public interface DatabaseManager {
    DatabaseManager createProductionInstance();
    DatabaseManager createTestInstance();
    ConnectionHandler getConnectionHandler();
    Transaction getTransactionHandler();
    String getDatabaseConnectionString();
    void destroySchema();
    void initSchema();
    void initData(String environment);
}
