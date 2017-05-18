package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.ItemDAO;
import by.bsuir.shop.dao.connectionpool.ConnectionPool;
import by.bsuir.shop.dao.connectionpool.exception.ConnectionPoolException;
import by.bsuir.shop.dao.connectionpool.factory.ConnectionPoolFactory;
import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.entity.ShopItem;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides methods for mysql db.
 * Implements {@link ItemDAO}
 */
public class MySQLItemDAO implements ItemDAO{
    private final static String GET_ALL_ITEMS = "select t1.*, avg(t2.review_rank) as rating from item t1 left join item_ranking t2 on (t1.item_id = t2.item_id) where t1.category = ? and t1.deleted = 0 group by t1.item_id";
    private final static String GET_ITEM_BY_ID = "select t1.*, avg(t2.review_rank) as rating from item t1 left join item_ranking t2 on (t1.item_id = t2.item_id) where t1.item_id = ? and t1.deleted = 0 group by t1.item_id";
    private final static String ADD_NEW_ITEM = "insert mydb.item(item_name, description, price, category, image) values (?, ?, ?, ?, ?)";
    private final static String EDIT_ITEM = "update mydb.item set item_name = ?, description = ?, price = ?, category = ?, image = ? where item_id = ?";
    private final static String DELETE_ITEM = "update mydb.item set deleted = 1 where item_id = ?";
    private final static String SEARCH_ITEMS = "select t1.*, avg(t2.review_rank) as rating from item t1 left join item_ranking t2 on (t1.item_id = t2.item_id) where t1.item_name like ? and t1.deleted = 0 group by t1.item_id";

    public final static String ATTR_ID = "item_id";
    public final static String ATTR_NAME = "item_name";
    public final static String ATTR_DESCRIPTION = "description";
    public final static String ATTR_PRICE = "price";
    public final static String ATTR_CATEGORY = "category";
    public final static String ATTR_IMAGE = "image";
    public final static String ATTR_RATING = "rating";

    private final static Logger logger = LogManager.getLogger(MySQLItemDAO.class.getName());

    /**
     * Loads items of selected category
     * @param category category name
     * @return list of items
     * @throws DAOException
     */
    @Override
    public List<ShopItem> getAllItems(String category) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<ShopItem> items = new ArrayList<>();

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try {
            connection = pool.takeConnection();

            statement = connection.prepareStatement(GET_ALL_ITEMS);
            statement.setString(1, category);
            System.out.println(statement);

            resultSet = statement.executeQuery();
            items = getResultList(resultSet);
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                resultSet.close();
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

        return items;
    }



    /**
     * Loads item info from database
     * @param id id of requested item
     * @return requsted item
     * @throws DAOException
     */
    @Override
    public ShopItem getItemById(Integer id) throws DAOException{
        ShopItem item = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try {
            connection = pool.takeConnection();

            statement = connection.prepareStatement(GET_ITEM_BY_ID);
            statement.setString(1, id.toString());

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                item = new ShopItem();

                String strRating = resultSet.getString(ATTR_RATING);
                Double rating = 0.0;
                if(strRating != null){
                    rating = Double.parseDouble(strRating);;
                }

                item.setId(id);
                item.setName(resultSet.getString(ATTR_NAME));
                item.setRating(rating.intValue());
                item.setDescription(resultSet.getString(ATTR_DESCRIPTION));
                item.setPrice(Double.parseDouble(resultSet.getString(ATTR_PRICE)));
                item.setCategory(resultSet.getString(ATTR_CATEGORY));
                item.setImageUrl(resultSet.getString(ATTR_IMAGE));
            }
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                resultSet.close();
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

        return item;
    }

    /**
     * Adds new item to database
     * @param item new item's info
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean addNewItem(ShopItem item) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        int countRows;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(ADD_NEW_ITEM);

            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setString(3, item.getPrice().toString());
            statement.setString(4, item.getCategory());
            statement.setString(5, item.getImageUrl());

            System.out.println(statement);

            countRows = statement.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

        if(countRows > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Edits item info
     * @param item new item's info
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean editItem(ShopItem item) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        int countRows;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            connection = pool.takeConnection();

            statement = connection.prepareStatement(EDIT_ITEM);

            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setString(3, item.getPrice().toString());
            statement.setString(4, item.getCategory());
            statement.setString(5, item.getImageUrl());
            statement.setString(6, item.getId().toString());

            countRows = statement.executeUpdate();
        } catch(SQLException e){
            logger.error(e);
            throw new DAOException(e);
        } catch (ConnectionPoolException e) {
            logger.error(e);
            throw new DAOException(e);
        } finally {
            try {
                statement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

        if(countRows > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Marks item as deleted
     * @param id item to be deleted
     * @return true, if operation was successful, otherwise - false
     * @throws DAOException
     */
    @Override
    public boolean deleteItem(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        int countRows = 0;

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try{
            ShopItem item = getItemById(id);

            connection = pool.takeConnection();

            deleteStatement = connection.prepareStatement(DELETE_ITEM);
            deleteStatement.setString(1, id.toString());

            countRows += deleteStatement.executeUpdate();
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                deleteStatement.close();
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            } catch (SQLException ex){
                logger.error(ex);
            }
        }

        if(countRows > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Suplementary method. Transforms result set into list of items
     * @param resultSet loaded result
     * @return list of items
     */
    private List<ShopItem> getResultList(ResultSet resultSet) {
        List<ShopItem> items = new ArrayList<>();
        ShopItem item = null;

        try {
            while(resultSet.next()){
                item = new ShopItem();

                String strRating = resultSet.getString(ATTR_RATING);
                Double rating;
                if(strRating == null){
                    rating = 0.0;
                } else {
                    rating = Double.parseDouble(strRating);
                }

                item.setId(Integer.parseInt(resultSet.getString(ATTR_ID)));
                item.setName(resultSet.getString(ATTR_NAME));
                item.setRating(rating.intValue());
                item.setDescription(resultSet.getString(ATTR_DESCRIPTION));
                item.setPrice(Double.parseDouble(resultSet.getString(ATTR_PRICE)));
                item.setCategory(resultSet.getString(ATTR_CATEGORY));
                item.setImageUrl(resultSet.getString(ATTR_IMAGE));
                items.add(item);
            }
        } catch (SQLException ex){
            logger.error(ex);
        }

        return items;
    }

    /**
     * Searchs items using querry
     * @param querry search querry
     * @return list of items
     * @throws DAOException
     */
    @Override
    public List<ShopItem> searchItems(String querry) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<ShopItem> items = new ArrayList<>();

        ConnectionPoolFactory factory = ConnectionPoolFactory.getInstance();
        ConnectionPool pool = factory.getPool();

        try {
            connection = pool.takeConnection();

            statement = connection.prepareStatement(SEARCH_ITEMS);

            String patternQuery = "%" + querry + "%";
            statement.setString(1, patternQuery);

            resultSet = statement.executeQuery();
            items = getResultList(resultSet);
        } catch (ConnectionPoolException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } catch (SQLException ex){
            logger.error(ex);
            throw new DAOException(ex);
        } finally {
            try {
                pool.returnConnection(connection);
            } catch (ConnectionPoolException ex){
                logger.error(ex);
            }
        }

        return items;
    }
}
