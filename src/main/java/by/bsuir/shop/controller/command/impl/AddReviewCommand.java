package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.entity.User;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.service.ReviewService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Command to add new review<br/>
 * Implements {@link Command}
 */
public class AddReviewCommand implements Command{
    private final static Logger logger = LogManager.getLogger(AddReviewCommand.class.getName());

    private final static String SRC_TEMPLATE = "Controller?action=gotoitem&item_id=";

    private final static String USER_ATTR = "user";
    private final static String USER_ID_ATTR = "user_id";
    private final static String ITEM_ID_ATTR = "item_id";
    private final static String COMMENT_ATTR = "comment";
    private final static String RATING_ATTR = "rating";

    /**
     * Collects item parameters and sends them to {@link ReviewService}
     * @param request incoming request.
     * @return page url
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        ReviewService reviewService = factory.getReviewService();

        User user = (User) request.getSession().getAttribute(USER_ATTR);
        String userId = user.getId().toString();

        Map<String, String> params = new HashMap<>();

        params.put(USER_ID_ATTR, userId);
        params.put(COMMENT_ATTR, request.getParameter(COMMENT_ATTR));
        params.put(RATING_ATTR, request.getParameter(RATING_ATTR));
        params.put(ITEM_ID_ATTR, request.getParameter(ITEM_ID_ATTR));

        String src = SRC_TEMPLATE + request.getParameter(ITEM_ID_ATTR);

        boolean status;
        try{
            status = reviewService.addReview(params);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return src;
    }
}
