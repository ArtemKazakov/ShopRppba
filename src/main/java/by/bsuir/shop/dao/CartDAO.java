package by.bsuir.shop.dao;

import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.Cart;

/**
 * Interface {@code CartDAO} is the class, that contains methods to work with cart info
 * in the data base.
 */
public interface CartDAO {
    public Cart getCart(Integer userId) throws DAOException;
    public boolean addItemToCart(Integer userId, Integer itemId, String size) throws DAOException;
    public boolean removeItemFromCart(Integer userId, Integer itemId, String size) throws DAOException;
    public boolean removeCart(Integer userId) throws DAOException;
}
