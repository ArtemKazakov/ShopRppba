package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.CartService;
import by.bsuir.shop.service.UserService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Command that logins the user<br/>
 * Implements {@link Command}
 */
public class LoginCommand implements Command{
    private static final String SRC="Controller?action=gotomain";
    private static final String WRONG_CREDS = "Controller?action=redirect&page=wrongcredentials.jsp";

    private static final String ATTR_EMAIL = "email";
    private static final String ATTR_PASSWORD = "password";
    private static final String ATTR_USER = "user";
    private static final String ATTR_CART = "cart";

    private final static Logger logger = LogManager.getLogger(LoginCommand.class.getName());

    /**
     * Checks user's credentials in database
     * @param request incoming request.
     * @return link to main page
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

            User user = service.login(email, password);
            if (user == null){
                return WRONG_CREDS;
            }

            Cart cart = cartService.getCart(user.getId().toString());

            if(cart == null){
                cart = new Cart();
            }

            request.getSession(true).setAttribute(ATTR_USER, user);
            request.getSession(true).setAttribute(ATTR_CART, cart);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return SRC;
    }
}

