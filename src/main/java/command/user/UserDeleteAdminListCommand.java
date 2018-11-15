package command.user;

import command.ActionCommand;
import command.exception.CommandException;
import entity.UserOrderNumber;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserDeleteAdminListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(UserDeleteAdminListCommand.class);

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        UserServiceImpl userService = new UserServiceImpl();
        List<UserOrderNumber> userOrderNumbers = null;
        try {
            userOrderNumbers = userService.findAllUsersWithOrderCount();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("users", userOrderNumbers);
        page = ConfigurationManager.getProperty("path.page.admin.delete.list.user");
        return page;
    }
}
