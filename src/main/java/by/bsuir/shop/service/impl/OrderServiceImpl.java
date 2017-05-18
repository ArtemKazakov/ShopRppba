package by.bsuir.shop.service.impl;

import by.bsuir.shop.dao.OrderDAO;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.dao.factory.DAOFactory;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.entity.Order;
import by.bsuir.shop.service.OrderService;
import by.bsuir.shop.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LogManager.getLogger(OrderServiceImpl.class.getName());

    @Override
    public boolean submitOrder(String userId, String delivery, String address, String comment, Cart cart) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO orderDAO = factory.getOrderDAO();

        if(!checkForNull(userId, delivery, address, comment)){
            return false;
        }

        if(!validateAddress(address)){
            return false;
        }

        boolean status;

        try {
            Integer userIntId = Integer.parseInt(userId);
            Integer needDelivery = Integer.parseInt(delivery);

            status = orderDAO.submitOrder(userIntId, needDelivery, address, comment, cart);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return status;
    }

    @Override
    public List<Order> getUserOrders(String userId) throws ServiceException{
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO orderDAO = factory.getOrderDAO();

        if(!checkForNull(userId)){
            return null;
        }

        List<Order> orders = new ArrayList<>();
        try {
            Integer userIntId = Integer.parseInt(userId);
            orders = orderDAO.getCustomerOrders(userIntId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return orders;
    }

    @Override
    public Order getOrder(String orderId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO orderDAO = factory.getOrderDAO();

        if(!checkForNull(orderId)){
            return null;
        }

        Order order;
        try {
            Integer orderIntId = Integer.parseInt(orderId);

            order = orderDAO.getOrder(orderIntId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return order;
    }

    @Override
    public boolean changeOrderStatus(String newStatus, String orderId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO orderDAO = factory.getOrderDAO();

        boolean status;

        if(!checkForNull(newStatus, orderId)){
            return false;
        }

        try {
            Integer orderIntId = Integer.parseInt(orderId);

            status = orderDAO.changeOrderStatus(newStatus, orderIntId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return status;
    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO orderDAO = factory.getOrderDAO();

        List<Order> orders = new ArrayList<>();
        try {
            orders = orderDAO.getAllOrders();
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return orders;
    }

    private boolean checkForNull(String ... args){
        for (String arg : args){
            if (arg == null){
                return false;
            }
        }

        return true;
    }

    private boolean validateAddress(String address){
        String addressPattern = "(?=.*\\d)[a-zA-Z]+([0-9a-zA-Z-_\\.,;:\\s]){14,}";

        return address.matches(addressPattern);
    }
}
