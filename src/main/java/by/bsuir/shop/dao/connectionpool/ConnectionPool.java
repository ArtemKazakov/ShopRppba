package by.bsuir.shop.dao.connectionpool;

import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;

import java.sql.Connection;

/**
 * Interface {@code ConnectionPool} has two methods to take connection and return to it to the queue.
 */
public interface ConnectionPool {
    /**
     * <p>Takes connection from the connection pool.</p>
     * @return {@code Connection} to connect to the data base.
     */
    Connection takeConnection() throws ConnectionPoolException;
    /**
     * <p>Returns connection to the pool.</p>
     * <p>
     * @param connection is the connection, that should be returned to the pool.
     */
    void returnConnection(Connection connection) throws ConnectionPoolException;
}
