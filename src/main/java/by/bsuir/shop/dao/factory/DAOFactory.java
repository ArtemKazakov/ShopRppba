package by.bsuir.shop.dao.factory;

import by.bsuir.shop.dao.*;
import by.bsuir.shop.dao.impl.*;


public class DAOFactory {
    private static final DAOFactory INSTANCE = new DAOFactory();

    private UserDAO userDAO = new MySQLUserDAO();
    private ItemDAO itemDAO = new MySQLItemDAO();
    private CartDAO cartDAO = new MySQLCartDAO();
    private OrderDAO orderDAO = new MySQLOrderDAO();
    private ReviewDAO reviewDAO = new MySQLReviewDAO();
    private SubscriptionDAO subscriptionDAO = new MySQLSubscriptionDAO();

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return INSTANCE;
    }

    public UserDAO getUserDAO(){
        return userDAO;
    }
    public ItemDAO getItemDAO() {
        return itemDAO;
    }
    public CartDAO getCartDAO(){
        return cartDAO;
    }
    public OrderDAO getOrderDAO(){
        return orderDAO;
    }
    public ReviewDAO getReviewDAO(){
        return reviewDAO;
    }
    public SubscriptionDAO getSubscriptionDAO(){
        return subscriptionDAO;
    }
}
