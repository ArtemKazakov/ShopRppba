package by.bsuir.shop.service.impl;

import by.bsuir.shop.dao.ReviewDAO;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.dao.factory.DAOFactory;
import by.bsuir.shop.entity.Review;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.ReviewService;
import by.bsuir.shop.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;


public class ReviewServiceImpl implements ReviewService {
    private final static Logger logger = LogManager.getLogger(ReviewServiceImpl.class.getName());

    private final static String USER_ID_ATTR = "user_id";
    private final static String ITEM_ID_ATTR = "item_id";
    private final static String COMMENT_ATTR = "comment";
    private final static String RATING_ATTR = "rating";

    @Override
    public List<Review> getItemReviews(String itemId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ReviewDAO reviewDAO = factory.getReviewDAO();

        if(!checkForNull(itemId)){
            return null;
        }

        List<Review> reviews = new ArrayList<>();
        Integer itemIntId;
        try{
            itemIntId = Integer.parseInt(itemId);
            reviews = reviewDAO.getItemReviews(itemIntId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return reviews;
    }

    @Override
    public boolean addReview(Map<String, String> params) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ReviewDAO reviewDAO = factory.getReviewDAO();

        for (String param : params.values()){
            if(!checkForNull(param)){
                return false;
            }
        }

        Review review = new Review();
        review.setUser(new User());

        boolean status;
        try {
            Integer userIntId = Integer.parseInt(params.get(USER_ID_ATTR));
            Integer itemIntId = Integer.parseInt(params.get(ITEM_ID_ATTR));
            Integer ratingInt = Integer.parseInt(params.get(RATING_ATTR));

            review.getUser().setId(userIntId);
            review.setRating(ratingInt);
            review.setItemId(itemIntId);
            review.setComment(params.get(COMMENT_ATTR));

            status = reviewDAO.addReview(review);
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
    public boolean removeReview(String itemId, String userId) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        ReviewDAO reviewDAO = factory.getReviewDAO();


        if(!checkForNull(userId, itemId)) {
            return false;
        }

        boolean status;
        Integer userIntId;
        Integer itemIntId;
        try {
            userIntId = Integer.parseInt(userId);
            itemIntId = Integer.parseInt(itemId);
            status = reviewDAO.removeReview(itemIntId, userIntId);
        } catch (NumberFormatException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return status;
    }

    private boolean checkForNull(String ... args){
        for (String arg : args){
            if (arg == null){
                return false;
            }
        }

        return true;
    }
}
