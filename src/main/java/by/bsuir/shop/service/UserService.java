package by.bsuir.shop.service;

import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.exception.ServiceException;

import java.util.Map;


public interface UserService {
    public User login(String emai, String name) throws ServiceException;
    public User register(Map<String, String> params) throws ServiceException;
}
