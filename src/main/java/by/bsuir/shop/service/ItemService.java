package by.bsuir.shop.service;

import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.service.exception.ServiceException;

import java.util.List;
import java.util.Map;


public interface ItemService {
    List<ShopItem> getAllItems(String category) throws ServiceException;
    List<ShopItem> searchItems(String querry) throws ServiceException;
    boolean addNewItem(Map<String, String> params) throws ServiceException;
    ShopItem loadItem(String id) throws ServiceException;
    boolean editItem(Map<String, String> params) throws ServiceException;
    boolean deleteItem(String id) throws ServiceException;
}
