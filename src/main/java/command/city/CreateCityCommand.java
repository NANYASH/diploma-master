package command.city;

import command.ActionCommand;
import command.exception.CommandException;
import controller.TravelController;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CityServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCityCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CreateCityCommand.class);

    private static final String PARAM_NAME_NAME = "name";

    private static final String PARAM_NAME_COUNTRY = "country";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String name = request.getParameter(PARAM_NAME_NAME);
        String countryId = request.getParameter(PARAM_NAME_COUNTRY);
        CityServiceImpl cityService = new CityServiceImpl();

        try{
            if (cityService.checkCreateCity(name, countryId)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorCreateCityPassMessage",
                        TravelController.messageManager.getProperty("message.createcityerror"));
                page = ConfigurationManager.getProperty("path.page.admin.create.city");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
