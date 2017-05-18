package by.bsuir.shop.dao;

import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.Review;

import java.util.List;

/**
 * Interface {@code ReviewDAO} is the class, that contains methods to work with reviews info
 * in the data base.
 */
public interface ReviewDAO {
    List<Review> getItemReviews(Integer itemId) throws DAOException;
    boolean addReview(Review review) throws DAOException;
    boolean removeReview(Integer itemId, Integer userId) throws DAOException;
}
