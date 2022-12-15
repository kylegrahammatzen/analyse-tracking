package net.analyse.base.database;

public interface AnalyseDatabase {

    /**
     * Executes a callback with a connection and automatically closes when finished
     * @param connectionCallback The callback to execute
     */
    void connect(ConnectionCallback connectionCallback);

    /**
     * Executes a callback with a connection and automatically closes when finished
     * @param connectionCallback The callback to execute
     * @param transaction Whether to use a transaction or not
     */
    void connect(ConnectionCallback connectionCallback, boolean transaction);

    /**
     * Closes all connections to the database
     */
    void closeConnection();

    /**
     * Whether all connections are completed.
     * @return true if all connections are completed, false otherwise.
     */
    boolean completed();

    /**
     * The lock to notify when all connections have been finalized
     * @return true if all connections are finalized, false otherwise.
     */
    Object getLock();

    /**
     * Cleans database data if needed
     */
    void clean();
}