package command.trip;

import command.ActionCommand;
import command.exception.CommandException;
import entity.City;
import entity.Trip;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CityServiceImpl;
import service.impl.TripServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditTripPageCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditTripPageCommand.class);

    private static final String PARAM_NAME_ID = "id";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));
        TripServiceImpl tripService = new TripServiceImpl();
        CityServiceImpl cityService = new CityServiceImpl();
        Trip trip = null;
        List<City> cities = null;
        try {
            trip = tripService.findEntityById(id);
            cities = cityService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == trip.getId()) {
            request.setAttribute("trip", trip);
            request.setAttribute("cities", cities);
            request.setAttribute("cities_size", trip.getCities().size());
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.trip");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }
}
