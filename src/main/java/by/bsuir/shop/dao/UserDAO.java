package by.bsuir.shop.dao;

import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.User;

import java.util.Map;

/**
 * Interface {@code UserDAO} is the class, that contains methods to work with users info
 * in the data base.
 */
public interface UserDAO {
    User getUserById(int id) throws DAOException;
    boolean checkUser(String email, String password) throws DAOException;
    User getUser(String email, String password) throws DAOException;
    boolean createUser(Map<String, String> parameters) throws DAOException;
}
