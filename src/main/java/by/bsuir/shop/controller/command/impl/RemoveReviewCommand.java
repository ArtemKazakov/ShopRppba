package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.service.ReviewService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that removes users review<br/>
 * Implements {@link Command}
 */
public class RemoveReviewCommand implements Command{
    private final static Logger logger = LogManager.getLogger(RemoveReviewCommand.class.getName());

    private final static String SRC_TEMPLATE = "Controller?action=gotoitem&item_id=";

    private final static String USER_ID_ATTR = "user_id";
    private final static String ITEM_ID_ATTR = "item_id";

    /**
     * Removes requested review from requested item
     * @param request incoming request.
     * @return link to current item
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        ReviewService reviewService = factory.getReviewService();

        String userId = request.getParameter(USER_ID_ATTR);
        String itemId = request.getParameter(ITEM_ID_ATTR);

        String src = SRC_TEMPLATE + itemId;

        boolean status;
        try{
            status = reviewService.removeReview(itemId, userId);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return src;
    }
}
