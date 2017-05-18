package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.service.CartService;
import by.bsuir.shop.service.OrderService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that submits order<br/>
 * Implements {@link Command}
 */
public class SubmitOrderCommand implements Command{
    private final static Logger logger = LogManager.getLogger(SubmitOrderCommand.class.getName());

    private static final String SRC="Controller?action=gotomain";

    private final static String ATTR_CART = "cart";
    private final static String ATTR_USER_ID = "user_id";
    private final static String ATTR_DELIVERY = "delivery";
    private final static String ATTR_ADDRESS = "address";
    private final static String ATTR_COMMENT = "comment";

    /**
     * Submits current order
     * @param request incoming request.
     * @return link to main page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        OrderService orderService = factory.getOrderService();
        CartService cartService = factory.getCartService();

        boolean status;

        Cart cart = (Cart) request.getSession().getAttribute(ATTR_CART);
        String userId = request.getParameter(ATTR_USER_ID);
        String delivery = request.getParameter(ATTR_DELIVERY);
        String address = request.getParameter(ATTR_ADDRESS);
        String comment = request.getParameter(ATTR_COMMENT);

        try {
            status = orderService.submitOrder(userId, delivery, address, comment, cart);
            cartService.removeCart(userId);
            cart.getItems().clear();
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return SRC;
    }
}
