package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.service.FileUploadService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Command for editing item<br/>
 * Implements {@link Command}
 */
public class EditItemCommand implements Command{
    public final static String ATTR_USER = "user";
    public final static String ATTR_ID = "item_id";
    public final static String ATTR_NAME = "item_name";
    public final static String ATTR_DESCRIPTION = "description";
    public final static String ATTR_PRICE = "price";
    public final static String ATTR_CATEGORY = "category";
    public final static String ATTR_IMAGE = "image";
    public final static String ATTR_CURR_IMAGE = "currentImg";
    public final static String IMAGE_DIR = "img/";
    public final static String COMMAND_TEMPLATE = "Controller?action=gotoitem&item_id=";

    public final static String CONTENT_DISPOSITION = "content-disposition";
    public final static String FILENAME = "filename";

    private final static Logger logger = LogManager.getLogger(EditItemCommand.class.getName());

    /**
     * Collects new item parameters and sends them to {@link ItemService}
     * @param request incoming request.
     * @return link to item page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String src = COMMAND_TEMPLATE + request.getParameter(ATTR_ID);

        ServiceFactory factory = ServiceFactory.getInstance();
        ItemService service = factory.getItemService();
        FileUploadService uploadService = factory.getFileUploadService();

        User user = (User) request.getSession().getAttribute(ATTR_USER);
        if(user.getRole() == ATTR_USER){
            throw new CommandException("invalid rights");
        }

        try{
            Map<String, String> params = new HashMap<>();
            Part image = request.getPart(ATTR_IMAGE);

            String imageName;
            if(!getFileName(image).isEmpty()){
                uploadService.uploadImage(image);
                imageName = IMAGE_DIR + getFileName(image);
            } else {
                imageName = request.getParameter(ATTR_CURR_IMAGE);
            }

            String name = request.getParameter(ATTR_NAME);
            String description = request.getParameter(ATTR_DESCRIPTION);
            String price = request.getParameter(ATTR_PRICE);
            String category = request.getParameter(ATTR_CATEGORY);
            String id = request.getParameter(ATTR_ID);

            params.put(ATTR_NAME, name);
            params.put(ATTR_DESCRIPTION, description);
            params.put(ATTR_PRICE, price);
            params.put(ATTR_CATEGORY, category);
            params.put(ATTR_IMAGE, imageName);
            params.put(ATTR_ID, id);

            service.editItem(params);
        } catch (ServiceException | IOException | ServletException ex) {
            logger.error(ex);
            throw new CommandException(ex);
        }
        return src;
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader(CONTENT_DISPOSITION);
        for (String content : part.getHeader(CONTENT_DISPOSITION).split(";")) {
            if (content.trim().startsWith(FILENAME)) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
