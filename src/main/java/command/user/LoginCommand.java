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

public class LoginCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(LoginCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    
    private static final String PARAM_NAME_PASSWORD = "password";
    
    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (userService.checkLogin(login, pass)) {
                User user = userService.findUserByName(login);
                HttpSession session = request.getSession(true);
                if (session.getAttribute("user") == null) {
                    session.setAttribute("user", user.getLogin());
                }
                if (session.getAttribute("role") == null) {
                    session.setAttribute("role", user.getRole());
                }
                if (session.getAttribute("iduser") == null) {
                    session.setAttribute("iduser", user.getId());
                }
                page = ConfigurationManager.getProperty("path.page.main");
            } else {
                request.setAttribute("errorLoginPassMessage",
                        TravelController.messageManager.getProperty("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.login");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }
}
