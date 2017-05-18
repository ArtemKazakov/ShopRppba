package by.bsuir.shop.service;

import by.bsuir.shop.entity.Review;
import by.bsuir.shop.service.exception.ServiceException;

import java.util.List;
import java.util.Map;


public interface ReviewService {
    List<Review> getItemReviews(String itemId) throws ServiceException;
    boolean addReview(Map<String, String> params) throws ServiceException;
    boolean removeReview(String itemId, String userId) throws ServiceException;
}
