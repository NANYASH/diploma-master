package command.shopping;

import command.ActionCommand;
import command.exception.CommandException;
import entity.City;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CityServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateShoppingPageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CreateShoppingPageCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        CityServiceImpl cityService = new CityServiceImpl();
        List<City> cities = null;
        try {
            cities = cityService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
            request.setAttribute("cities", cities);
            page = ConfigurationManager.getProperty("path.page.admin.create.shopping");
        return page;
    }

}
