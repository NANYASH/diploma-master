package command.trip;

import command.ActionCommand;
import command.exception.CommandException;
import controller.TravelController;
import entity.City;
import entity.Trip;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CityServiceImpl;
import service.impl.TripServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditTripCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditTripCommand.class);

    private static final String PARAM_NAME_ID = "id";
    
    private static final String PARAM_NAME_NAME = "name";
    
    private static final String PARAM_NAME_SUMMARY = "summary";
    
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure-date";
    
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival-date";
    
    private static final String PARAM_NAME_ARRIVAL_ATTRACTIONS = "attractions";
    
    private static final String PARAM_NAME_LAST_MINUTE = "last-minute";
    
    private static final String PARAM_NAME_PRICE = "price";
    
    private static final String PARAM_NAME_TRANSPORT = "transport";
    
    private static final String PARAM_NAME_SERVICES = "services";
    
    private static final String PARAM_NAME_DESCRIPTION = "description";

    private static final String PARAM_NAME_COUNT_CITIES = "count-cities";

    private static final String PARAM_NAME_CITY = "city";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        String id = request.getParameter(PARAM_NAME_ID);
        String name = request.getParameter(PARAM_NAME_NAME);
        String summary = request.getParameter(PARAM_NAME_SUMMARY);
        String departureDate = request.getParameter(PARAM_NAME_DEPARTURE_DATE);
        String arrivalDate = request.getParameter(PARAM_NAME_ARRIVAL_DATE);
        String attractions = request.getParameter(PARAM_NAME_ARRIVAL_ATTRACTIONS);
        String lastMinute = request.getParameter(PARAM_NAME_LAST_MINUTE);
        LOG.debug("Last minute test: " + request.getParameter(PARAM_NAME_LAST_MINUTE));
        String price = request.getParameter(PARAM_NAME_PRICE);
        String transport = request.getParameter(PARAM_NAME_TRANSPORT);
        String services = request.getParameter(PARAM_NAME_SERVICES);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        TripServiceImpl tripService = new TripServiceImpl();
        CityServiceImpl cityService = new CityServiceImpl();
        try {
            int countCities = Integer.parseInt(request.getParameter(PARAM_NAME_COUNT_CITIES));
            LOG.debug("countCities = " + countCities);
            ArrayList<City> cities = new ArrayList<>();
            for (int i = 1; i <= countCities; i++) {
                City city = cityService.findEntityById(Long.parseLong(request.getParameter(PARAM_NAME_CITY+i)));
                LOG.debug("City to add - " + city.getName());
                cities.add(city);
            }

            if ("application/octet-stream".equals(request.getPart("img").getContentType())){
                if (tripService.checkEditTrip(id, name, summary, departureDate, arrivalDate, attractions, lastMinute, price, transport, services, description, cities)) {
                    page = ConfigurationManager.getProperty("path.page.admin.panel");
                }
                else {
                    request.setAttribute("errorEditTripPassMessage",
                            TravelController.messageManager.getProperty("message.edittriperror"));
                    page = createEditTripPage(request, Long.parseLong(id));
                }
            }
            else {
                String SAVE_DIR = "images" + File.separator + "trips";
                String appPath = request.getServletContext().getRealPath("");
                String savePath = appPath + SAVE_DIR;

                LOG.debug("Save Path = " + savePath);
                Part filePart = request.getPart("img");

                if (tripService.checkEditTrip(id, name, summary, departureDate, arrivalDate, attractions, lastMinute, price, transport, services, description, filePart, savePath,cities)) {
                    page = ConfigurationManager.getProperty("path.page.admin.panel");
                }
                else {
                    request.setAttribute("errorEditTripPassMessage",
                            TravelController.messageManager.getProperty("message.edittriperror"));
                    page = createEditTripPage(request, Long.parseLong(id));
                }
            }
        } catch (IOException | ServletException e) {
            throw new CommandException("Failed to get parts from request.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

    private String createEditTripPage(HttpServletRequest request, Long id) throws CommandException {
        String page = null;
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
