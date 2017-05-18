package by.bsuir.shop.dao.connectionpool.factory;

import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.impl.ConnectionPoolImpl;


public class ConnectionPoolFactory {
    private static final ConnectionPoolFactory INSTANCE = new ConnectionPoolFactory();

    private ConnectionPool pool = new ConnectionPoolImpl();

    private ConnectionPoolFactory(){}

    public static ConnectionPoolFactory getInstance(){
        return INSTANCE;
    }

    public ConnectionPool getPool(){
        return pool;
    }
}
