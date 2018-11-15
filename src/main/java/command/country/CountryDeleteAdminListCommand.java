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
import java.util.List;

public class CountryDeleteAdminListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CountryDeleteAdminListCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        CountryServiceImpl countryService = new CountryServiceImpl();
        List<Country> countries = null;
        try {
            countries = countryService.findAllCountries();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("countries", countries);
        page = ConfigurationManager.getProperty("path.page.admin.delete.list.country");
        return page;
    }
}
