package cz.muni.fi.pv168.project.storage.sql.db;

public interface TransactionExecutor {
    void executeInTransaction(Runnable operation);
}
