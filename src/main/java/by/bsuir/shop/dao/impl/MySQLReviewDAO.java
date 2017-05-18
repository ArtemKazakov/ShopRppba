package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.ReviewDAO;
import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;
import by.bsuir.shop.dao.connectionpool.factory.ConnectionPoolFactory;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.Review;
import by.bsuir.shop.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides methods for mysql db.
 * Implements {@link ReviewDAO}
 */
public class MySQLReviewDAO implements ReviewDAO{
    private final static Logger logger = LogManager.getLogger(MySQLReviewDAO.class.getName());

    private final static String GET_REVIEWS = "select t1.*, t2.customer_name, t2.customer_surname from item_ranking t1 join customer t2  on (t1.customer_id = t2.customer_id) where t1.item_id = ?";
    private final static String ADD_REVIEW = "insert item_ranking(item_id, customer_id, rank_date, review_rank, comment) values (?, ?, ?, ?, ?)";
    private final static String REMOVE_REVIEW = "delete from item_ranking where item_id = ? and customer_id = ?";

    private final static String USER_ID_ATTR = "customer_id";
    private final static String USER_NAME_ATTR = "customer_name";
    private final static String USER_SURNAME_ATTR = "customer_surname";
    private final static String REVIEW_RANK_ATTR = "review_rank";
    private final static String REVIEW_DATE_ATTR = "rank_date";
    private final static String ITEM_ID_ATTR = "item_id";
    private final static String REVIEW_COMMENT_ATTR = "comment";


    /**
     * Loads items review
     * @param itemId id of the item
     * @return list of reviews
     * @throws DAOException
     */
    @Override
    public List<Review> getItemReviews(Integer itemId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Review> reviews = new ArrayList<>();

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try {
            connection = pool.takeConnection();

            statement = connection.prepareStatement(GET_REVIEWS);
            statement.setString(1, itemId.toString());

            resultSet = statement.executeQuery();

            User user;
            Review review;
            while (resultSet.next()){
                user = new User();
                review = new Review();

                user.setId(Integer.parseInt(resultSet.getString(USER_ID_ATTR)));
                user.setName(resultSet.getString(USER_NAME_ATTR));
                user.setSurname(resultSet.getString(USER_SURNAME_ATTR));
                System.out.println(user.getName());
                review.setUser(user);

                review.setRating(Integer.parseInt(resultSet.getString(REVIEW_RANK_ATTR)));
                review.setReviewDate(resultSet.getDate(REVIEW_DATE_ATTR));
                review.setItemId(Integer.parseInt(resultSet.getString(ITEM_ID_ATTR)));
                review.setComment(resultSet.getString(REVIEW_COMMENT_ATTR));

                reviews.add(review);
            }

        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                statement.close();
            } catch (SQLException ex){
                logger.error(ex);
            }
            try {
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            }
        }

        return reviews;
    }

    /**
     * Adds review
     * @param review review to be added
     * @return true, if addition was successful, otherwise - false;
     * @throws DAOException
     */
    @Override
    public boolean addReview(Review review) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        Integer countRows = 0;
        try {
            connection = pool.takeConnection();

            statement = connection.prepareStatement(ADD_REVIEW);
            statement.setString(1, review.getItemId().toString());
            statement.setString(2, review.getUser().getId().toString());

            Time time = new Time(new java.util.Date().getTime());
            Date date = new Date(new java.util.Date().getTime());

            String reviewDate = date.toString() + " " + time.toString();

            statement.setString(3, reviewDate);
            statement.setString(4, review.getRating().toString());
            statement.setString(5, review.getComment());

            countRows = statement.executeUpdate();
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                statement.close();
            } catch (SQLException ex){
                logger.error(ex);
            }
            try {
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            }
        }

        if (countRows > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes review from selected item
     * @param itemId id of item
     * @param userId id of review's author
     * @return true, if operation was successful, otherwise - false;
     * @throws DAOException
     */
    @Override
    public boolean removeReview(Integer itemId, Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        Integer countRows = 0;
        try {
            connection = pool.takeConnection();

            statement = connection.prepareStatement(REMOVE_REVIEW);
            statement.setString(1, itemId.toString());
            statement.setString(2, userId.toString());

            countRows = statement.executeUpdate();
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                statement.close();
            } catch (SQLException ex){
                logger.error(ex);
            }
            try {
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            }
        }

        if (countRows > 0){
            return true;
        } else {
            return false;
        }
    }
}
