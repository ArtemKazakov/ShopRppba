package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.CartService;
import by.bsuir.shop.service.UserService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Command to register new user<br/>
 * Implements {@link Command}
 */
public class RegisterCommand implements Command {
    private static final String SRC="Controller?action=gotomain";
    private static final String ATTR_NAME = "customer_name";
    private static final String ATTR_SURNAME = "customer_surname";
    private static final String ATTR_EMAIL = "email";
    private static final String ATTR_PASSWORD = "password";
    private static final String ATTR_ADDRESS = "customer_address";
    private static final String ATTR_GENDER = "gender";
    private static final String ATTR_BIRTHDAY = "birthday";
    private static final String ATTR_USER = "user";
    private static final String ATTR_CART = "cart";

    private final static Logger logger = LogManager.getLogger(RegisterCommand.class.getName());

    /**
     * Collects item parameters and sends them to {@link UserService}
     * @param request incoming request.
     * @return main page link
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            UserService service = factory.getUserService();
            CartService cartService = factory.getCartService();

            String email = request.getParameter(ATTR_EMAIL);
            String password = request.getParameter(ATTR_PASSWORD);
            String name = request.getParameter(ATTR_NAME);
            String surname = request.getParameter(ATTR_SURNAME);
            String address = request.getParameter(ATTR_ADDRESS);
            String gender = request.getParameter(ATTR_GENDER);
            String birthday = request.getParameter(ATTR_BIRTHDAY);

            Map<String, String> params = new HashMap<>();

            params.put(ATTR_EMAIL, email);
            params.put(ATTR_BIRTHDAY, birthday);
            params.put(ATTR_PASSWORD, password);
            params.put(ATTR_NAME, name);
            params.put(ATTR_SURNAME, surname);
            params.put(ATTR_ADDRESS, address);
            params.put(ATTR_GENDER, gender);


            User user = service.register(params);
            Cart cart = cartService.getCart(user.getId().toString());

            if(cart == null){
                cart = new Cart();
            }

            request.getSession(true).setAttribute(ATTR_USER, user);
            request.getSession(true).setAttribute(ATTR_CART, cart);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return SRC;
    }
}
