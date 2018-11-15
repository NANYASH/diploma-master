package command.shopping;

import command.ActionCommand;
import command.exception.CommandException;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.ShoppingServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteShoppingCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(DeleteShoppingCommand.class);

    private static final String PARAM_NAME_ID = "id";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        try {
            shoppingService.delete(id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        page = ConfigurationManager.getProperty("path.page.admin.panel");
        return page;
    }

}
