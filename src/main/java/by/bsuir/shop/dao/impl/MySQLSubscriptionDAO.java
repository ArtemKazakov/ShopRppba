package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.SubscriptionDAO;
import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;
import by.bsuir.shop.dao.connectionpool.factory.ConnectionPoolFactory;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.Subscription;
import by.bsuir.shop.entity.User;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLSubscriptionDAO implements SubscriptionDAO {
    private final static Logger logger = LogManager.getLogger(MySQLSubscriptionDAO.class.getName());

    private static final String INSERT_SUBSCRIPTION_QUERY = "INSERT INTO `subscription` " +
            "(`customer_id`, `category`) " +
            "VALUES (?, ?) ";
    private static final String DELETE_SUBSCRIPTION_QUERY = "DELETE FROM `subscription` " +
            " WHERE customer_id = ? AND category = ? ";
    private static final String SELECT_ALL_SUBSCRIPTIONS_BY_CATEGORY_QUERY = "SELECT * FROM `subscription` WHERE `category` = ? ";
    private static final String SELECT_ALL_SUBSCRIPTIONS_BY_USER_QUERY = "SELECT * FROM `subscription` WHERE `customer_id` = ? ";

    private static final String SELECT_ALL_SUBSCRIPTIONS_QUERY = "SELECT * FROM `subscription` ";


    @Override
    public void addSubscription(Subscription subscription) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(INSERT_SUBSCRIPTION_QUERY);

            statement.setInt(1, subscription.getUser().getId());
            statement.setString(2, subscription.getCategory());

            statement.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

    }

    @Override
    public void deleteSubscription(int userId, String category) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(DELETE_SUBSCRIPTION_QUERY);

            statement.setInt(1, userId);
            statement.setString(2, category);

            statement.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }
    }

    @Override
    public List<Subscription> getById(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(SELECT_ALL_SUBSCRIPTIONS_BY_USER_QUERY);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();

            List<Subscription> subscriptions = new ArrayList<>();

            while (resultSet.next()){
                Subscription subscription = new Subscription();
                subscription.setCategory(resultSet.getString(3));
                User user = new User();
                user.setId(resultSet.getInt(2));
                subscription.setUser(user);
                subscriptions.add(subscription);
            }

            return subscriptions;
        } catch(SQLException e){
            logger.error(e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }
    }

    @Override
    public List<Subscription> getByCategory(String category) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(SELECT_ALL_SUBSCRIPTIONS_BY_CATEGORY_QUERY);
            statement.setString(1, category);

            resultSet = statement.executeQuery();

            List<Subscription> subscriptions = new ArrayList<>();

            while (resultSet.next()){
                Subscription subscription = new Subscription();
                subscription.setCategory(resultSet.getString(3));
                User user = new User();
                user.setId(resultSet.getInt(2));
                subscription.setUser(user);
                subscriptions.add(subscription);
            }

            return subscriptions;
        } catch(SQLException e){
            logger.error(e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }
    }

    @Override
    public List<Subscription> getAll() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(SELECT_ALL_SUBSCRIPTIONS_QUERY);

            resultSet = statement.executeQuery();

            List<Subscription> subscriptions = new ArrayList<>();

            while (resultSet.next()){
                Subscription subscription = new Subscription();
                subscription.setCategory(resultSet.getString(3));
                User user = new User();
                user.setId(resultSet.getInt(2));
                subscription.setUser(user);
                subscriptions.add(subscription);
            }

            return subscriptions;
        } catch(SQLException e){
            logger.error(e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

    }
}
