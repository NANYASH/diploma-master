package command.country;

import command.ActionCommand;
import command.exception.CommandException;
import entity.Country;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CountryServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCountryPageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditCountryPageCommand.class);

    private static final String PARAM_NAME_ID = "id";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        CountryServiceImpl countryService = new CountryServiceImpl();
        Country country = null;
        try {
            country = countryService.findEntityById(id);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == country.getId()) {
            request.setAttribute("country", country);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.country");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }

}
