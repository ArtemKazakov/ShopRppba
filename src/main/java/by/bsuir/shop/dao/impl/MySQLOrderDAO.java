package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.OrderDAO;
import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;
import by.bsuir.shop.dao.connectionpool.factory.ConnectionPoolFactory;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.Order;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.entity.Cart;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Class that provides methods for mysql db.
 * Implements {@link OrderDAO}
 */
public class MySQLOrderDAO implements OrderDAO {
    private final static Logger logger = LogManager.getLogger(MySQLOrderDAO.class.getName());

    private final static String ADD_ORDER = "insert mydb.customer_order(customer, order_price, order_date, comment, delivery, address) values(?, ?, ?, ?, ?, ?)";
    private final static String ADD_ITEMS_TO_ORDER = "INSERT mydb.item_order(order_id, item_id, item_size, quantity) values(?, ?, ?, ?)";
    private final static String GET_ALL_ORDERS = "select * from customer_order t1 join customer t2 on (t1.customer = t2.customer_id)";
    private final static String GET_CUSTOMER_ORDERS = "select * from customer_order where customer = ?";
    private final static String GET_ORDER = "select t1.item_id, t1.item_size, t1.quantity, t2.item_name, t2.price, t2.image, t3.*, t4.customer_name, t4.customer_surname, t4.email from item_order t1 join item t2 on (t1.item_id = t2.item_id) join customer_order t3 on (t1.order_id = t3.order_id) join customer t4 on (t3.customer = t4.customer_id) where t1.order_id = ?";
    private final static String CHANGE_STATUS = "update mydb.customer_order set status = ? where order_id = ?";


    private final static String ORDER_ID_ATTR = "order_id";
    private final static String CUSTOMER_ID_ATTR = "customer";
    private final static String CUSTOMER_NAME_ATTR = "customer_name";
    private final static String CUSTOMER_SURNAME_ATTR = "customer_surname";
    private final static String CUSTOMER_EMAIL_ATTR = "email";
    private final static String ORDER_ADDRESS = "address";
    private final static String ORDER_COMMENT = "comment";
    private final static String ORDER_DELIVERY = "delivery";
    private final static String ORDER_STATUS = "status";
    private final static String ORDER_PRICE = "order_price";
    private final static String ORDER_DATE = "order_date";
    private final static String ATTR_ID = "item_id";
    private final static String ATTR_SIZE = "item_size";
    private final static String ATTR_NAME = "item_name";
    public final static String ATTR_DESCRIPTION = "description";
    private final static String ATTR_PRICE = "price";
    public final static String ATTR_CATEGORY = "category";
    private final static String ATTR_IMAGE = "image";
    private final static String QNT_ATTR = "quantity";


    /**
     * Submits order
     * @param userId id of user
     * @param delivery delivery request
     * @param address address for delivery
     * @param comment orders comment
     * @param cart cart with items
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean submitOrder(Integer userId, Integer delivery, String address, String comment, Cart cart) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement addStatement = null;
        ResultSet resultSet = null;

        int countRows = 0;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            Time time = new Time(new java.util.Date().getTime());
            Date date = new Date(new java.util.Date().getTime());

            String orderDate = date.toString() + " " + time.toString();

            statement = connection.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            addStatement = connection.prepareStatement(ADD_ITEMS_TO_ORDER);

            statement.setString(1, userId.toString());
            statement.setString(2, cart.getCost().toString());
            statement.setString(3, orderDate);
            statement.setString(4, comment);
            statement.setString(5, delivery.toString());
            statement.setString(6, address);

            countRows = statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();

            resultSet.next();
            String orderId = resultSet.getString(1);

            ShopItem item;
            Iterator iterator = cart.getItems().keySet().iterator();
            while (iterator.hasNext()){
                item = (ShopItem) iterator.next();
                addStatement.setString(1, orderId);
                addStatement.setString(2, item.getId().toString());
                addStatement.setString(3, item.getSize());
                addStatement.setString(4, cart.getItems().get(item).toString());

                System.out.println(addStatement);
                addStatement.executeUpdate();
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
                addStatement.close();
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
     * Loads all orders from db
     * @return list of all orders
     * @throws DAOException
     */
    @Override
    public List<Order> getAllOrders() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        List<Order> orders = new ArrayList<>();
        Order order = null;
        User user = null;

        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(GET_ALL_ORDERS);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                order = new Order();
                user = new User();

                user.setId(Integer.parseInt(resultSet.getString(CUSTOMER_ID_ATTR)));
                user.setName(resultSet.getString(CUSTOMER_NAME_ATTR));
                user.setSurname(resultSet.getString(CUSTOMER_SURNAME_ATTR));
                user.setEmail(resultSet.getString(CUSTOMER_EMAIL_ATTR));
                order.setCustomer(user);

                order.setOrderDate(resultSet.getDate(ORDER_DATE));
                order.setId(Integer.parseInt(resultSet.getString(ORDER_ID_ATTR)));
                order.setAddress(resultSet.getString(ORDER_ADDRESS));
                order.setPrice(Double.parseDouble(resultSet.getString(ORDER_PRICE)));
                order.setComment(resultSet.getString(ORDER_COMMENT));
                order.setStatus(resultSet.getString(ORDER_STATUS));

                if (resultSet.getString(ORDER_DELIVERY).equals("0")) {
                    order.setDelivery(false);
                } else {
                    order.setDelivery(true);
                }

                orders.add(order);
            }
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        }

        return orders;
    }


    /**
     * Load orders of selected customer
     * @param customerId id of customer;
     * @return list of customer's orders;
     * @throws DAOException
     */
    @Override
    public List<Order> getCustomerOrders(Integer customerId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        List<Order> orders = new ArrayList<>();
        Order order = null;

        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(GET_CUSTOMER_ORDERS);
            statement.setString(1, customerId.toString());

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                order = new Order();
                order.setOrderDate(resultSet.getDate(ORDER_DATE));
                order.setId(Integer.parseInt(resultSet.getString(ORDER_ID_ATTR)));
                order.setAddress(resultSet.getString(ORDER_ADDRESS));
                order.setStatus(resultSet.getString(ORDER_STATUS));
                order.setPrice(Double.parseDouble(resultSet.getString(ORDER_PRICE)));
                order.setComment(resultSet.getString(ORDER_COMMENT));

                if (resultSet.getString(ORDER_DELIVERY).equals("0")) {
                    order.setDelivery(false);
                } else {
                    order.setDelivery(true);
                }

                orders.add(order);
            }
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        }

        return orders;
    }

    /**
     * Loads requested order
     * @param orderId id of requested order
     * @return requested order info
     * @throws DAOException
     */
    @Override
    public Order getOrder(Integer orderId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        Map<ShopItem, Integer> items = new HashMap<>();
        Order order = null;
        User user = null;
        ShopItem item = null;
        Integer quantity;

        try {
            connection = pool.takeConnection();
            statement = connection.prepareStatement(GET_ORDER);
            statement.setString(1, orderId.toString());

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                order = new Order();
                user = new User();

                user.setId(Integer.parseInt(resultSet.getString(CUSTOMER_ID_ATTR)));
                user.setName(resultSet.getString(CUSTOMER_NAME_ATTR));
                user.setSurname(resultSet.getString(CUSTOMER_SURNAME_ATTR));
                user.setEmail(resultSet.getString(CUSTOMER_EMAIL_ATTR));
                order.setCustomer(user);

                order.setOrderDate(resultSet.getDate(ORDER_DATE));
                order.setStatus(resultSet.getString(ORDER_STATUS));
                order.setId(Integer.parseInt(resultSet.getString(ORDER_ID_ATTR)));
                order.setAddress(resultSet.getString(ORDER_ADDRESS));
                order.setPrice(Double.parseDouble(resultSet.getString(ORDER_PRICE)));
                order.setComment(resultSet.getString(ORDER_COMMENT));

                do {
                    quantity = Integer.parseInt(resultSet.getString(QNT_ATTR));

                    item = new ShopItem();

                    item.setId(Integer.parseInt(resultSet.getString(ATTR_ID)));
                    item.setSize(resultSet.getString(ATTR_SIZE));
                    item.setPrice(Double.parseDouble(resultSet.getString(ATTR_PRICE)) * quantity);
                    item.setName(resultSet.getString(ATTR_NAME));
                    item.setImageUrl(resultSet.getString(ATTR_IMAGE));

                    items.put(item, quantity);
                } while (resultSet.next());

                order.setItems(items);
            }

        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        }

        return order;
    }

    /**
     * Changes orders status
     * @param status new status
     * @param orderId order id
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean changeOrderStatus(String status, Integer orderId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        Integer countRows = 0;
        try {
            connection = pool.takeConnection();

            statement = connection.prepareStatement(CHANGE_STATUS);
            statement.setString(1, status);
            statement.setString(2, orderId.toString());

            countRows = statement.executeUpdate();

        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        }

        if (countRows > 0) {
            return true;
        }

        return false;
    }
}
