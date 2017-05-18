package by.bsuir.shop.service.impl;

import by.bsuir.shop.dao.SubscriptionDAO;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.dao.factory.DAOFactory;
import by.bsuir.shop.entity.Subscription;
import by.bsuir.shop.service.SubscriptionService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.exception.SubscribeAlreadyExistException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {
    private final static Logger logger = LogManager.getLogger(SubscriptionServiceImpl.class.getName());

    @Override
    public void addSubscription(Subscription subscription) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        SubscriptionDAO dao = factory.getSubscriptionDAO();

        try {

            List<Subscription> subscriptions = dao.getAll();

            for (Subscription sub : subscriptions){
                if (sub.getUser().getId().equals(subscription.getUser().getId()) && sub.getCategory().equals(subscription.getCategory())){
                    throw new SubscribeAlreadyExistException();
                }
            }

            dao.addSubscription(subscription);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

    }

    @Override
    public void deleteSubscription(int userId, String category) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        SubscriptionDAO dao = factory.getSubscriptionDAO();

        try {

            List<Subscription> subscriptions = dao.getAll();

            boolean exist = false;
            for (Subscription sub : subscriptions){
                if (sub.getUser().getId().equals(userId)){
                    exist = true;
                }
            }

            if (exist) {
                dao.deleteSubscription(userId, category);
            }
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Subscription> getByUser(int userId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        SubscriptionDAO dao = factory.getSubscriptionDAO();
        List<Subscription> subscriptions = null;
        try {

            subscriptions = dao.getById(userId);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }
        return subscriptions;

    }
}
