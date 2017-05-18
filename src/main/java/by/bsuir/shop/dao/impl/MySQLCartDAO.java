package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.CartDAO;
import by.bsuir.shop.dao.ItemDAO;
import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;
import by.bsuir.shop.dao.connectionpool.factory.ConnectionPoolFactory;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.dao.factory.DAOFactory;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.entity.ShopItem;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that provides methods for mysql db.
 * Implements {@link CartDAO}
 */
public class MySQLCartDAO implements CartDAO{
    private final static Logger logger = LogManager.getLogger(MySQLItemDAO.class.getName());

    private final static String GET_ROW = "select * from mydb.cart where customer_id = ? and item_id = ? and item_size = ?";
    private final static String UPDATE_QUANTITY = "update mydb.cart set quantity = ? where customer_id = ? and item_id = ? and item_size = ?";
    private final static String GET_CART = "select * from mydb.cart where customer_id = ?";
    private final static String ADD_ITEM_TO_CART = "insert mydb.cart(customer_id, item_id, item_size) values (?, ?, ?)";
    private final static String DELETE_ITEM_FROM_CART = "delete from mydb.cart where customer_id = ? and item_id = ? and item_size = ?";
    private final static String DELETE_CART = "delete from mydb.cart where customer_id = ?";

    private final static String ITEM_ID_ATTR = "item_id";
    private final static String SIZE_ATTR = "item_size";
    private final static String QNT_ATTR = "quantity";

    /**
     * Loads user's cart from db
     * @param userId current user
     * @return user's cart
     * @throws DAOException
     */
    @Override
    public Cart getCart(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Cart cart = new Cart();

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(GET_CART);
            statement.setString(1, userId.toString());

            resultSet = statement.executeQuery();

            ShopItem item = null;

            DAOFactory daoFactory = DAOFactory.getInstance();
            ItemDAO itemDao = daoFactory.getItemDAO();

            Integer quantity;
            while (resultSet.next()){
                quantity = Integer.parseInt(resultSet.getString(QNT_ATTR));
                for (int i = 0; i < quantity; i++){
                    item = itemDao.getItemById(Integer.parseInt(resultSet.getString(ITEM_ID_ATTR)));
                    if(item != null) {
                        item.setSize(resultSet.getString(SIZE_ATTR));
                        cart.addItem(item);
                    }
                }
            }

        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException();
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException();
        } finally {
            try {
                statement.close();
                resultSet.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

        return cart;
    }

    /**
     * Adds item to cart
     * @param userId user id
     * @param itemId item id
     * @param size item size
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean addItemToCart(Integer userId, Integer itemId, String size) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int countRows = 0;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(GET_ROW);
            statement.setString(1, userId.toString());
            statement.setString(2, itemId.toString());
            statement.setString(3, size);

            resultSet = statement.executeQuery();

            if (!resultSet.next()){
                statement = connection.prepareStatement(ADD_ITEM_TO_CART);
                statement.setString(1, userId.toString());
                statement.setString(2, itemId.toString());
                statement.setString(3, size);

                countRows = statement.executeUpdate();
            } else {
                Integer quantity = Integer.parseInt(resultSet.getString(QNT_ATTR));

                quantity++;

                statement = connection.prepareStatement(UPDATE_QUANTITY);
                statement.setString(1, quantity.toString());
                statement.setString(2, userId.toString());
                statement.setString(3, itemId.toString());
                statement.setString(4, size);

                countRows = statement.executeUpdate();
            }

        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException();
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException();
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

        if(countRows > 0){
            return true;
        }

        return false;
    }

    /**
     * Removes item from cart. Deletes it from database.
     * @param userId user id
     * @param itemId item id
     * @param size item size
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean removeItemFromCart(Integer userId, Integer itemId, String size) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int countRows = 0;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(GET_ROW);
            statement.setString(1, userId.toString());
            statement.setString(2, itemId.toString());
            statement.setString(3, size);

            resultSet = statement.executeQuery();
            resultSet.next();
            Integer quantity = Integer.parseInt(resultSet.getString(QNT_ATTR));

            if(quantity > 1){
                quantity--;
                statement = connection.prepareStatement(UPDATE_QUANTITY);
                statement.setString(1, quantity.toString());
                statement.setString(2, userId.toString());
                statement.setString(3, itemId.toString());
                statement.setString(4, size);

                countRows = statement.executeUpdate();
            } else {
                statement = connection.prepareStatement(DELETE_ITEM_FROM_CART);
                statement.setString(1, userId.toString());
                statement.setString(2, itemId.toString());
                statement.setString(3, size);

                countRows = statement.executeUpdate();
            }


        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException();
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException();
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

        if(countRows > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clears users data from cart table.
     * @param userId user id
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean removeCart(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        int countRows = 0;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(DELETE_CART);
            statement.setString(1, userId.toString());

            countRows = statement.executeUpdate();
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException();
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException();
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

        if(countRows > 0){
            return true;
        }

        return false;
    }
}
