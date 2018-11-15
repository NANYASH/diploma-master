package command.country;

import command.ActionCommand;
import command.exception.CommandException;
import controller.TravelController;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CountryServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCountryCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CreateCountryCommand.class);

    private static final String PARAM_NAME_NAME = "name";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String name = request.getParameter(PARAM_NAME_NAME);
        CountryServiceImpl countryService = new CountryServiceImpl();

        try{
            if (countryService.checkCreateCountry(name)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorCreateCountryPassMessage",
                        TravelController.messageManager.getProperty("message.createcountryerror"));
                page = ConfigurationManager.getProperty("path.page.admin.create.country");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
