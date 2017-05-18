package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.Subscription;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.SubscriptionService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.exception.SubscribeAlreadyExistException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ASUS on 04.05.2017.
 */
public class DeleteSubscriptionCommand implements Command {
    private final static Logger logger = LogManager.getLogger(AddReviewCommand.class.getName());

    private final static String USER_ATTR = "user";
    private final static String CATEGORY_ATTR = "category";


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        SubscriptionService subscriptionService = factory.getSubscriptionService();

        User user = (User) request.getSession().getAttribute(USER_ATTR);

        String src = "Controller?action=load&category=" + request.getParameter(CATEGORY_ATTR)+"&subError=";

        try{
            subscriptionService.deleteSubscription(user.getId(), request.getParameter(CATEGORY_ATTR));
            src += false;
        } catch (SubscribeAlreadyExistException e){
            logger.error(e);
            src += true;
        }
        catch (ServiceException ex){
            logger.error(ex);
            src += true;
            throw new CommandException(ex);
        }

        return src;

    }
}
