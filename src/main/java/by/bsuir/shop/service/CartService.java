package by.bsuir.shop.service;

import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.entity.Cart;


public interface CartService {
    public Cart getCart(String userId) throws ServiceException;
    public boolean addItemToCart(String userId, String itemId, String size) throws ServiceException;
    public boolean removeItemFromCart(String userId, String itemId, String size) throws ServiceException;
    public boolean removeCart(String userId) throws ServiceException;
}
