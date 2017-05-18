package by.bsuir.shop.service.impl;

import by.bsuir.shop.dao.CartDAO;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.dao.factory.DAOFactory;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.service.CartService;
import by.bsuir.shop.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class CartServiceImpl implements CartService{
    private final static Logger logger = LogManager.getLogger(ItemServiceImpl.class.getName());

    @Override
    public Cart getCart(String userId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        CartDAO cartDAO = factory.getCartDAO();

        Cart cart = new Cart();

        if (userId == null){
            return null;
        }

        try {
            Integer userIntId = Integer.parseInt(userId);
            cart = cartDAO.getCart(userIntId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return cart;
    }

    @Override
    public boolean addItemToCart(String userId, String itemId, String size) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        CartDAO cartDAO = factory.getCartDAO();

        boolean status;

        if ((userId == null) || (itemId == null)){
            return false;
        }

        try {
            Integer userIntId = Integer.parseInt(userId);
            Integer itemIntId = Integer.parseInt(itemId);

            status = cartDAO.addItemToCart(userIntId, itemIntId, size);

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
    public boolean removeItemFromCart(String userId, String itemId, String size) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        CartDAO cartDAO = factory.getCartDAO();

        boolean status;

        if ((userId == null) || (itemId == null)){
            return false;
        }

        try {
            Integer userIntId = Integer.parseInt(userId);
            Integer itemIntId = Integer.parseInt(itemId);

            status = cartDAO.removeItemFromCart(userIntId, itemIntId, size);
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
    public boolean removeCart(String userId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        CartDAO cartDAO = factory.getCartDAO();

        boolean status;

        if (userId == null){
            return false;
        }

        try {
            Integer userIntId = Integer.parseInt(userId);
            status = cartDAO.removeCart(userIntId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException();
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return status;
    }
}
