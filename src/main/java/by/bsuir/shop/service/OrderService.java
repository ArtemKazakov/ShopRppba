package by.bsuir.shop.service;

import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.entity.Order;

import java.util.List;


public interface OrderService {
    boolean submitOrder(String userId, String delivery, String address, String comment, Cart cart) throws ServiceException;
    List<Order> getUserOrders(String userId) throws ServiceException;
    List<Order> getAllOrders() throws ServiceException;
    Order getOrder(String orderId) throws ServiceException;
    boolean changeOrderStatus(String status, String orderId) throws ServiceException;
}
