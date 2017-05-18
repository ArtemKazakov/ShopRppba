package by.bsuir.shop.dao.connectionpool.impl;

import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.DBResourceManager;
import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ConnectionPoolImpl implements ConnectionPool {
    private BlockingQueue<Connection> connectionsQueue;
    private BlockingQueue<Connection> workingConnectionsQueue;
    private String url;
    private String user;
    private String password;
    private String driverLocation;
    private int connection_amount;

    private static final String KEY_URL = "db.url";
    private static final String KEY_USER = "db.user";
    private static final String KEY_PASSWORD = "db.password";
    private static final String KEY_LOCATION_OF_DRIVER = "db.driver";
    private static final String KEY_CONNECTION_AMOUNT="db.amount";
    private static final int DEFAULT_AMOUNT = 15;

    private final static Logger logger = LogManager.getLogger(ConnectionPoolImpl.class.getName());

    public ConnectionPoolImpl() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        this.url = dbResourceManager.getValue(KEY_URL);
        this.user = dbResourceManager.getValue(KEY_USER);
        this.password = dbResourceManager.getValue(KEY_PASSWORD);
        this.driverLocation = dbResourceManager.getValue(KEY_LOCATION_OF_DRIVER);
        try {
            this.connection_amount = Integer.parseInt(dbResourceManager.getValue(KEY_CONNECTION_AMOUNT));
        } catch(NumberFormatException e){
            this.connection_amount = DEFAULT_AMOUNT;
        }
        this.connectionsQueue = new ArrayBlockingQueue<Connection>(connection_amount);
        this.workingConnectionsQueue = new ArrayBlockingQueue<Connection>(connection_amount);

        try {
            init();
        } catch (ConnectionPoolException e) {
            logger.error(e.getMessage());
        }
    }

    private void init() throws ConnectionPoolException {
        try {
            Class.forName(driverLocation);

            for(int i = 0; i < connection_amount; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionsQueue.put(connection);
            }

        } catch (SQLException ex) {
            logger.error(ex);
            throw new ConnectionPoolException(ex);
        } catch (InterruptedException ex){
            logger.error(ex);
            throw new ConnectionPoolException(ex);
        } catch (ClassNotFoundException ex){
            logger.error(ex);
            throw new ConnectionPoolException(ex);
        }
    }

    private void destroy() throws ConnectionPoolException{
        try {
            closeConnectionQueue(connectionsQueue);
        } catch (ConnectionPoolException e) {
            logger.error(e.getMessage());
        }
        try {
            closeConnectionQueue(workingConnectionsQueue);
        } catch (ConnectionPoolException e) {
            logger.error(e.getMessage());
        }
    }

    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws ConnectionPoolException {
        for(Connection connection: queue){
            try {
                if (connection != null) {
                    if (!connection.getAutoCommit()) {
                        connection.commit();
                    }
                    connection.close();
                }
            } catch (SQLException e){
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionsQueue.remove();
            workingConnectionsQueue.put(connection);
        } catch (InterruptedException e) {
            logger.error(e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    @Override
    public void returnConnection(Connection connection) throws ConnectionPoolException {
        try {
            connectionsQueue.put(connection);
            workingConnectionsQueue.remove(connection);
        } catch (InterruptedException e) {
            logger.error(e);
            throw new ConnectionPoolException("Interrupted exception");
        }

    }
}
