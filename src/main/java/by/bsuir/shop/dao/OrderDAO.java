package by.bsuir.shop.dao;

import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.entity.Order;

import java.util.List;

/**
 * Interface {@code ItemDAO} is the class, that contains methods to work with orders info
 * in the data base.
 */
public interface OrderDAO {
    boolean submitOrder(Integer userId, Integer delivery, String address, String comment, Cart cart) throws DAOException;
    List<Order> getAllOrders() throws DAOException;
    List<Order> getCustomerOrders(Integer customerId) throws DAOException;
    Order getOrder(Integer orderId) throws DAOException;
    boolean changeOrderStatus(String status, Integer orderId) throws DAOException;
}
