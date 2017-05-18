package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.OrderService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.entity.Order;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command that loads user info<br/>
 * Implements {@link Command}
 */
public class GoToUserProfileCommand implements Command{
    private final static Logger logger = LogManager.getLogger(GoToUserProfileCommand.class.getName());

    private final static String src = "/WEB-INF/jsp/profile.jsp";

    private final static String USER_ATTR = "user";
    private final static String ORDERS_ATTR = "orders";
    private final static String ORDERS_SIZE_ATTR = "orders_size";

    /**
     * Loads user info
     * @param request incoming request.
     * @return link to user profile
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        OrderService orderService = factory.getOrderService();

        List<Order> orders;
        User user;
        try {
            user = (User) request.getSession().getAttribute(USER_ATTR);
            if(user.getRole().equals(USER_ATTR)){
                orders = orderService.getUserOrders(user.getId().toString());
                System.out.println(orders.size() + " size");
            } else {
                orders = orderService.getAllOrders();
            }

            request.setAttribute(ORDERS_ATTR, orders);
            request.setAttribute(ORDERS_SIZE_ATTR, orders.size());
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return src;
    }
}