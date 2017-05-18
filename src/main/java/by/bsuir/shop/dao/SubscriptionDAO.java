package by.bsuir.shop.dao;

import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.Subscription;

import java.util.List;

public interface SubscriptionDAO {

    void addSubscription(Subscription subscription) throws DAOException;
    void deleteSubscription(int userId, String category) throws DAOException;
    List<Subscription> getById(int userId) throws DAOException;
    List<Subscription> getByCategory(String category) throws DAOException;
    List<Subscription> getAll() throws DAOException;
}
