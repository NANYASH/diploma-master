package command.shopping;

import command.ActionCommand;
import command.exception.CommandException;
import entity.City;
import entity.Shopping;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CityServiceImpl;
import service.impl.ShoppingServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditShoppingPageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditShoppingPageCommand.class);

    private static final String PARAM_NAME_ID = "id";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        CityServiceImpl cityService = new CityServiceImpl();
        Shopping shopping = null;
        List<City> cities = null;
        try {
            shopping = shoppingService.findEntityById(id);
            cities = cityService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == shopping.getId()) {
            request.setAttribute("shopping", shopping);
            request.setAttribute("cities", cities);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.shopping");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }
}
