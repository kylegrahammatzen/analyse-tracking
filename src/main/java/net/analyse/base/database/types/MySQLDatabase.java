package net.analyse.base.database.types;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.analyse.base.database.AnalyseDatabase;
import net.analyse.base.database.ConnectionCallback;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class MySQLDatabase implements AnalyseDatabase {
    private final Logger logger;
    private HikariDataSource hikariDataSource;
    private final AtomicInteger openConnections;
    private final Object lock;

    /**
     * Creates a new MySQLDatabase instance.
     * @param logger The logger to use.
     * @param hostname The hostname of the database.
     * @param port The port of the database.
     * @param database The database name.
     * @param username The username to use.
     * @param password The password to use.
     * @param useSSL Whether to use SSL.
     */
    public MySQLDatabase(Logger logger, String hostname, int port, String database, String username, String password, boolean useSSL) {
        this.logger = logger;
        this.openConnections = new AtomicInteger();
        this.lock = new Object();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=" + useSSL + "&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(3);

        try {
            this.hikariDataSource = new HikariDataSource(config);
        } catch (Exception ex) {
            logger.severe("Could not connect to the MySQL database. Please check your configuration.");
            ex.printStackTrace();
        }
    }

    /**
     * Executes a callback with a connection and automatically closes when finished
     * @param connectionCallback The callback to execute
     */
    @Override
    public void connect(ConnectionCallback connectionCallback) {
        this.openConnections.incrementAndGet();
        try (Connection connection = this.hikariDataSource.getConnection()) {
            connectionCallback.accept(connection);
        } catch (SQLException ex) {
            logger.severe("Could not execute MySQL statement: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            int open = this.openConnections.decrementAndGet();
            synchronized (this.lock) {
                if (open == 0) {
                    this.lock.notify();
                }
            }
        }
    }

    /**
     * Executes a callback with a connection and automatically closes when finished
     * @param connectionCallback The callback to execute
     * @param transaction Whether to use a transaction or not
     */
    @Override
    public void connect(ConnectionCallback connectionCallback, boolean transaction) {
        this.connect(connectionCallback);
    }

    /**
     * Closes all connections to the database
     */
    @Override
    public void closeConnection() {
        this.hikariDataSource.close();
    }

    /**
     * Whether all connections are completed.
     * @return true if all connections are completed, false otherwise.
     */
    @Override
    public boolean completed() {
        return this.openConnections.get() == 0;
    }

    /**
     * The lock to notify when all connections have been finalized
     * @return true if all connections are finalized, false otherwise.
     */
    @Override
    public Object getLock() {
        return this.lock;
    }

    /**
     * Cleans database data if needed
     */
    @Deprecated
    public void clean() {

    }
}
