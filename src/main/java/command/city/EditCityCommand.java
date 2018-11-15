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

public class EditCityCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditCityCommand.class);

    private static final String PARAM_NAME_ID = "id";
    
    private static final String PARAM_NAME_NAME = "name";

    private static final String PARAM_NAME_COUNTRY = "country";



    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;

        String id = request.getParameter(PARAM_NAME_ID);
        String name = request.getParameter(PARAM_NAME_NAME);
        String countryId = request.getParameter(PARAM_NAME_COUNTRY);
        CityServiceImpl cityService = new CityServiceImpl();

        try {
            if (cityService.checkEditCity(id,name,countryId)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorEditCityPassMessage",
                        TravelController.messageManager.getProperty("message.editcityerror"));
                page = ConfigurationManager.getProperty("path.page.admin.edit.info.city");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
