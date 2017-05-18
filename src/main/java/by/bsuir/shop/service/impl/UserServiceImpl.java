package by.bsuir.shop.service.impl;

import by.bsuir.shop.dao.exception.DAOException;
import by.bsuir.shop.dao.factory.DAOFactory;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.UserService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.dao.UserDAO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;


public class UserServiceImpl implements UserService {
    private static final String ATTR_NAME = "customer_name";
    private static final String ATTR_SURNAME = "customer_surname";
    private static final String ATTR_EMAIL = "email";
    private static final String ATTR_PASSWORD = "password";
    private static final String ATTR_ADDRESS = "customer_address";
    private static final String ATTR_GENDER = "gender";
    private static final String ATTR_BIRTHDAY = "birthday";

    private final static Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());

    @Override
    public User login(String email, String password) throws ServiceException {
        User user = null;
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO dao = factory.getUserDAO();
        try {
            if ((email == null)||(password == null)){
                throw new ServiceException("Wrong credentials");
            }

            if(!validateEmail(email)){
                throw new ServiceException("Wrong credentials");
            }

            boolean status = dao.checkUser(email, password);

            if (status){
                user = dao.getUser(email, password);
            } else{
                return null;
            }
        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return user;
    }

    @Override
    public User register(Map<String, String> params) throws ServiceException {
        User user = null;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO dao = factory.getUserDAO();

            if(!validateParams(params)){
                throw new ServiceException("invalid params");
            }

            boolean status = dao.createUser(params);

            if(status){
                user = dao.getUser(params.get(ATTR_EMAIL), params.get(ATTR_PASSWORD));
            } else{
                throw new ServiceException("error");
            }

        } catch (DAOException ex){
            logger.error(ex);
            throw new ServiceException(ex);
        }

        return user;
    }

    private boolean validateEmail (String email){
        String emailRegEx = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";

        return email.matches(emailRegEx);
    }

    private boolean validateParams(Map<String, String> params){
        String name = params.get(ATTR_NAME);
        if ((name == null) || (name.isEmpty())) {
            logger.error("invalid name");
            return false;
        }

        String surname = params.get(ATTR_SURNAME);
        if ((surname == null) || (surname.isEmpty())) {
            logger.error("invalid surname");
            return false;
        }

        String email = params.get(ATTR_EMAIL);
        if ((email == null) || (email.isEmpty())) {
            logger.error("invalid email");
            return false;
        }

        String password = params.get(ATTR_PASSWORD);
        if ((password == null) || (password.isEmpty())){
            logger.error("invalid password");
            return false;
        }

        String address = params.get(ATTR_ADDRESS);
        if ((address == null) || (address.isEmpty())){
            logger.error("invalid address");
            return false;
        }

        String gender = params.get(ATTR_GENDER);
        if ((gender == null) || (gender.isEmpty())) {
            logger.error("invalid gender");
            return false;
        }

        String birthday = params.get(ATTR_GENDER);
        if ((birthday == null) || (birthday.isEmpty())){
            logger.error("invalid birthday");
            return false;
        }


        String nameRegEx = "[a-zA-Z]+([-\'][a-zA-Z]+)*";
        String surnameRegex = "[a-zA-Z]+([-\'\\s][a-zA-Z]+)*";
        String emailRegEx = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";
        String passwordRegEx = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,25}";
        String addressRegEx = "(?=.*\\d)[a-zA-Z]+([0-9a-zA-Z-_\\.,;:\\s]){14,}";

        if (!name.matches(nameRegEx)){
            logger.error("invalid nm");
            return false;
        }
        if (!surname.matches(surnameRegex)){
            logger.error("invalid sur");
            return false;
        }
        if (!email.matches(emailRegEx)){
            logger.error("invalid mail");
            return false;
        }
        if (!password.matches(passwordRegEx)){
            logger.error("invalid pass");
            return false;
        }
        if (!address.matches(addressRegEx)){
            logger.error("invalid ad");
            return false;
        }

        return true;
    }
}
