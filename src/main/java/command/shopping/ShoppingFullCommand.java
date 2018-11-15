package command.shopping;

import command.ActionCommand;
import command.exception.CommandException;
import entity.Shopping;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.ShoppingServiceImpl;
import service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShoppingFullCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(ShoppingFullCommand.class);

    private static final String PARAM_NAME_ID = "id";
    
    private static final String PARAM_NAME_ID_USER = "iduser";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        Long iduser = (Long) request.getSession().getAttribute(PARAM_NAME_ID_USER);
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        UserServiceImpl userService = new UserServiceImpl();
        try {
            Shopping shopping = shoppingService.findEntityById(id);
            if (id == shopping.getId()) {
                request.setAttribute("shopping", shopping);
                if (iduser != null) {
                    request.setAttribute("userProfile", userService.findEntityById(iduser));
                }
                page = ConfigurationManager.getProperty("path.page.shopping.full");
            } else {
                page = ConfigurationManager.getProperty("path.page.main");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
