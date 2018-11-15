package command.user;

import command.ActionCommand;
import command.exception.CommandException;
import controller.TravelController;
import entity.User;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BalancePageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(BalancePageCommand.class);

    private static final String PARAM_NAME_ID_USER = "iduser";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        HttpSession session = request.getSession();
        if (session.getAttribute(PARAM_NAME_ID_USER) != null) {
            Long userId = (Long) session.getAttribute(PARAM_NAME_ID_USER);
            UserServiceImpl userService = new UserServiceImpl();
            User user = null;
            try {
                user = userService.findEntityById(userId);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            request.setAttribute("userProfile", user);
            page = ConfigurationManager.getProperty("path.page.balance");
        } else {
            request.setAttribute("errorNotAuthorizedMessage",
                    TravelController.messageManager.getProperty("message.notauthorizederror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}
