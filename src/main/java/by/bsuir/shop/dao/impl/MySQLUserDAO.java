package by.bsuir.shop.dao.impl;

import by.bsuir.shop.entity.User;
import by.bsuir.shop.dao.UserDAO;
import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;
import by.bsuir.shop.dao.connectionpool.factory.ConnectionPoolFactory;
import by.bsuir.shop.dao.exception.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Class that provides methods for mysql db.
 * Implements {@link UserDAO}
 */
public class MySQLUserDAO implements UserDAO{
    private static final String GET_USER_BY_ID_SQL = "select * from mydb.customer where customer_id=? ";
    private static final String EMAIL_PASSWORD_CHECK_SQL = "select * from mydb.customer where email=? and password=?";
    private static final String CREATE_USER_SQL = "insert mydb.customer(email, password, customer_name, customer_surname, customer_address, birthday, gender) values(?, ?, ?, ?, ?, ?, ?)";

    private static final String ATTR_NAME = "customer_name";
    private static final String ATTR_SURNAME = "customer_surname";
    private static final String ATTR_EMAIL = "email";
    private static final String ATTR_PASSWORD = "password";
    private static final String ATTR_ADDRESS = "customer_address";
    private static final String ATTR_GENDER = "gender";
    private static final String ATTR_BIRTHDAY = "birthday";
    private static final String ATTR_ID = "customer_id";
    private static final String ATTR_ROLE = "role";

    private final static Logger logger = LogManager.getLogger(MySQLUserDAO.class.getName());

    @Override
    public User getUserById(int id) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();
        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(GET_USER_BY_ID_SQL);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                user = new User();

                user.setEmail(resultSet.getString(ATTR_EMAIL));
                user.setPassword(resultSet.getString(ATTR_PASSWORD));
                user.setId(Integer.parseInt(resultSet.getString(ATTR_ID)));
                user.setName(resultSet.getString(ATTR_NAME));
                user.setSurname(resultSet.getString(ATTR_SURNAME));
                user.setAddress(resultSet.getString(ATTR_ADDRESS));
                user.setBirthday(resultSet.getDate(ATTR_BIRTHDAY));
                user.setGender(resultSet.getString(ATTR_GENDER));
                user.setRole(resultSet.getString(ATTR_ROLE));
            }

        } catch(SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (ConnectionPoolException ex) {
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                resultSet.close();
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }
        return user;
    }

    /**
     * Method that checks user credentials in database
     * @param email users email
     * @param password users password
     * @return true, if credentials are ok, otherwise - false;
     * @throws DAOException
     */
    @Override
    public boolean checkUser(String email, String password) throws DAOException {
        Connection connection = null;
        boolean status = false;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();
        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(EMAIL_PASSWORD_CHECK_SQL);
            statement.setString(1, email);
            statement.setString(2, password);
            System.out.println(statement);
            resultSet = statement.executeQuery();

            if( resultSet.next() ){
                status = true;
            }

        } catch(SQLException | ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                resultSet.close();
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }
        return status;
    }

    /**
     * Creates user in database
     * @param parameters
     * @return true, if creation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean createUser(Map<String, String> parameters) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;
        int countRows;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(CREATE_USER_SQL);

            statement.setString(1, parameters.get(ATTR_EMAIL));
            statement.setString(2, parameters.get(ATTR_PASSWORD));
            statement.setString(3, parameters.get(ATTR_NAME));
            statement.setString(4, parameters.get(ATTR_SURNAME));
            statement.setString(5, parameters.get(ATTR_ADDRESS));
            statement.setString(6, parameters.get(ATTR_BIRTHDAY));
            statement.setString(7, parameters.get(ATTR_GENDER));

            countRows = statement.executeUpdate();
        } catch(SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (ConnectionPoolException ex) {
            logger.error(ex);
            throw new DAOException(ex);
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
        if( countRows > 0){
            return true;
        } else {
            return false;
        }

    }

    /**
     * Gets users info
     * @param email user's email
     * @param password user's password
     * @return User with requested params
     * @throws DAOException
     */
    @Override
    public User getUser(String email, String password) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();
        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(EMAIL_PASSWORD_CHECK_SQL);
            statement.setString(1, email);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                user = new User();

                user.setEmail(email);
                user.setPassword(password);
                user.setId(Integer.parseInt(resultSet.getString(ATTR_ID)));
                user.setName(resultSet.getString(ATTR_NAME));
                user.setSurname(resultSet.getString(ATTR_SURNAME));
                user.setAddress(resultSet.getString(ATTR_ADDRESS));
                user.setBirthday(resultSet.getDate(ATTR_BIRTHDAY));
                user.setGender(resultSet.getString(ATTR_GENDER));
                user.setRole(resultSet.getString(ATTR_ROLE));
            }

        } catch(SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (ConnectionPoolException ex) {
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                resultSet.close();
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }
        return user;
    }
}
