package command.user;

import command.ActionCommand;
import command.exception.CommandException;
import controller.TravelController;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(RegisterCommand.class);

    private static final String PARAM_NAME_LOGIN = "login";
    
    private static final String PARAM_NAME_PASSWORD = "password";
    
    private static final String PARAM_NAME_EMAIL = "email";
    
    private static final String PARAM_NAME_NAME = "name";
    
    private static final String PARAM_NAME_SURNAME = "surname";
    

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        LOG.debug("Encoding (Register): " + request.getCharacterEncoding());
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (userService.checkRegister(login, pass, email, name, surname)) {
                page = ConfigurationManager.getProperty("path.page.login");
            }
            else {
                request.setAttribute("errorRegisterPassMessage",
                        TravelController.messageManager.getProperty("message.registererror"));
                page = ConfigurationManager.getProperty("path.page.register");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
