package cz.muni.fi.pv168.project.storage.sql.db;

import java.io.Closeable;

public interface Transaction extends Closeable {

    ConnectionHandler connection();

    void commit();

    void close();

    boolean isClosed();
}
