package by.bsuir.shop.service;


import by.bsuir.shop.entity.Subscription;
import by.bsuir.shop.service.exception.ServiceException;

import java.util.List;

public interface SubscriptionService {
    void addSubscription(Subscription subscription) throws ServiceException;
    void deleteSubscription(int userId, String category) throws ServiceException;
    List<Subscription> getByUser(int userId) throws ServiceException;
}
