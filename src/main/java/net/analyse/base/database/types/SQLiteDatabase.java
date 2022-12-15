package net.analyse.base.database.types;

import net.analyse.base.database.AnalyseDatabase;
import net.analyse.base.database.ConnectionCallback;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class SQLiteDatabase implements AnalyseDatabase {
    private final Logger logger;
    private final String connectionString;
    private Connection connection;
    private final AtomicInteger openConnections;
    private final Object lock;

    /**
     * Creates a new SQLiteDatabase instance.
     * @param logger The logger to use.
     * @param directory The directory to use.
     */
    public SQLiteDatabase(Logger logger, String directory) {
        this.logger = logger;
        this.connectionString = "jdbc:sqlite:" + directory + "/backup.db";
        this.openConnections = new AtomicInteger();
        this.lock = new Object();

        try {
            Class.forName("org.sqlite.JDBC");

            File tmpDirectory = new File(directory, "tmp");

            if (!tmpDirectory.exists()) {
                if (!tmpDirectory.mkdirs()) {
                    logger.severe("Could not create temporary directory for SQLite database.");
                }
            }

            System.setProperty("org.sqlite.tmpdir", tmpDirectory.getAbsolutePath());
        } catch (ClassNotFoundException e) {
            logger.severe("Could not load SQLite driver. Please check your configuration.");
            e.printStackTrace();
        }
    }

    /**
     * Executes a callback with a connection and automatically closes when finished
     * @param connectionCallback The callback to execute
     */
    @Override
    public void connect(ConnectionCallback connectionCallback) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection(this.connectionString);
                this.connection.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            logger.severe("Error occurred retrieving the SQLite database connection: " + ex.getMessage());
        }

        this.openConnections.incrementAndGet();
        try {
            if (this.connection.getAutoCommit()) {
                this.connection.setAutoCommit(false);
            }

            connectionCallback.accept(this.connection);
            try {
                this.connection.commit();
            } catch (SQLException e) {
                if (e.getMessage() != null && !e.getMessage().contains("transaction")) {
                    throw e;
                }

                try {
                    this.connection.rollback();
                } catch (SQLException ignored) { }
            }
        } catch (Exception ex) {
            logger.severe("Error occurred executing an SQLite query: " + ex.getMessage());
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
        if (transaction) {
            this.connect(connectionCallback);
            return;
        }

        try {
            if (this.connection == null || this.connection.isClosed())
                this.connection = DriverManager.getConnection(this.connectionString);
        } catch (SQLException ex) {
            logger.severe("Error occurred retrieving the SQLite database connection: " + ex.getMessage());
        }

        this.openConnections.incrementAndGet();
        try {
            if (!this.connection.getAutoCommit()) {
                this.connection.setAutoCommit(true);
            }

            connectionCallback.accept(this.connection);
        } catch (Exception ex) {
            logger.severe("Error occurred executing an SQLite query: " + ex.getMessage());
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
     * Closes connection to the database
     */
    @Override
    public void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException ex) {
            logger.severe("Error occurred closing SQLite database connection: " + ex.getMessage());
        }
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
    @Override
    public void clean() {
        this.connect(connection -> {
            try {
                connection.createStatement().execute("VACUUM");
            } catch (Exception e) {
                logger.warning("Failed to run vacuum on backup, unable to access temp directory: no read/write access.");
            }
        }, false);
    }
}