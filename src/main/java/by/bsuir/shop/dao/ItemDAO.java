package by.bsuir.shop.dao;

import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.ShopItem;

import java.util.List;

/**
 * Interface {@code ItemDAO} is the class, that contains methods to work with items info
 * in the data base.
 */
public interface ItemDAO {
    List<ShopItem> getAllItems(String category) throws DAOException;
    ShopItem getItemById(Integer id) throws DAOException;
    boolean editItem(ShopItem item) throws DAOException;
    boolean addNewItem(ShopItem item) throws DAOException;
    boolean deleteItem(Integer id) throws DAOException;
    List<ShopItem> searchItems(String querry) throws DAOException;
}
