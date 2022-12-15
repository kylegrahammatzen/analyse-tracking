package net.analyse.base.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCallback {
    /**
     * Wraps a connection in a callback that automatically handles sql exceptions.
     * @param connection The connection to wrap.
     * @throws SQLException If there is an error with the connection.
     */
    void accept(Connection connection) throws SQLException;
}
