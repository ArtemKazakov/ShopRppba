package by.bsuir.shop.controller;

import by.bsuir.shop.controller.command.impl.*;
import by.bsuir.shop.controller.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code ControllerHelper} is the class, that contains enumeration of all commands and the map, where key is a
 * name of the command, and object is new object of Command class, user wants to call.
 */

public class ControllerHelper {
    private Map<CommandName, Command> commands = new HashMap<>();

    public ControllerHelper(){
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.REGISTER, new RegisterCommand());
        commands.put(CommandName.LOAD, new LoadAllItemsCommand());
        commands.put(CommandName.ADDNEWITEM, new AddNewItemCommand());
        commands.put(CommandName.REDIRECT, new RedirectCommand());
        commands.put(CommandName.GOTOEDITITEM, new GoToItemEditCommand());
        commands.put(CommandName.EDITITEM, new EditItemCommand());
        commands.put(CommandName.GOTOITEM, new GoToItemCommand());
        commands.put(CommandName.GOTOMAIN, new GoToMainCommand());
        commands.put(CommandName.DELETEITEM, new DeleteItemCommand());
        commands.put(CommandName.ADDTOCART, new AddToCartCommand());
        commands.put(CommandName.REMOVEFROMCART, new RemoveFromCartCommand());
        commands.put(CommandName.SUBMITORDER, new SubmitOrderCommand());
        commands.put(CommandName.GOTOUSERPROFILE, new GoToUserProfileCommand());
        commands.put(CommandName.GOTOORDER, new GoToOrderCommand());
        commands.put(CommandName.ADDREVIEW, new AddReviewCommand());
        commands.put(CommandName.DELETEREVIEW, new RemoveReviewCommand());
        commands.put(CommandName.CHANGELOCALE, new ChangeLocaleCommand());
        commands.put(CommandName.CHANGEORDERSTATUS, new ChangeOrderStatusCommand());
        commands.put(CommandName.SEARCH, new SearchCommand());
        commands.put(CommandName.ADDSUBSCRIPTION, new AddSubscriptionCommand());
        commands.put(CommandName.DELETESUBSCRIPTION, new DeleteSubscriptionCommand());
    }

    /**
     * <p>Transforms command name to upper case.</p>
     * @param commandName is a name of the requested command
     * @return {@code String} contains the name of the command.
     * @see javax.servlet.ServletException
     * @see javax.servlet.http.HttpServletRequest
     * @see javax.servlet.http.HttpServletResponse
     * @see java.util.Enumeration
     * @see HashMap
     */
    public Command getCommand(String commandName){
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        return commands.get(name);
    }

    enum CommandName{
        LOGIN, LOGOUT, REGISTER, LOAD, ADDNEWITEM, EDITITEM,REDIRECT, GOTOEDITITEM, GOTOITEM, GOTOMAIN,
        DELETEITEM, ADDTOCART, REMOVEFROMCART, SUBMITORDER, GOTOUSERPROFILE, GOTOORDER, ADDREVIEW, DELETEREVIEW,
        CHANGELOCALE, CHANGEORDERSTATUS, SEARCH, ADDSUBSCRIPTION, DELETESUBSCRIPTION
    }
}