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

    @Override
    public void closeConnection() {
        this.hikariDataSource.close();
    }

    @Override
    public void connect(ConnectionCallback callback) {
        this.openConnections.incrementAndGet();
        try (Connection connection = this.hikariDataSource.getConnection()) {
            callback.accept(connection);
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

    @Override
    public void connect(ConnectionCallback callback, boolean transaction) {
        this.connect(callback);
    }

    @Override
    public Object getLock() {
        return this.lock;
    }

    @Override
    public boolean completed() {
        return this.openConnections.get() == 0;
    }

    @Override
    public void clean() {

    }
}
