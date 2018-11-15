package command.vacation;

import command.ActionCommand;
import command.exception.CommandException;
import controller.TravelController;
import entity.City;
import entity.Vacation;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.CityServiceImpl;
import service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EditVacationCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(EditVacationCommand.class);

    private static final String PARAM_NAME_ID = "id";
    
    private static final String PARAM_NAME_NAME = "name";
    
    private static final String PARAM_NAME_SUMMARY = "summary";
    
    private static final String PARAM_NAME_DEPARTURE_DATE = "departure-date";
    
    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival-date";
    
    private static final String PARAM_NAME_DESTINATION_CITY = "destination-city";
    
    private static final String PARAM_NAME_HOTEL = "hotel";
    
    private static final String PARAM_NAME_LAST_MINUTE = "last-minute";
    
    private static final String PARAM_NAME_PRICE = "price";
    
    private static final String PARAM_NAME_TRANSPORT = "transport";
    
    private static final String PARAM_NAME_SERVICES = "services";
    
    private static final String PARAM_NAME_DESCRIPTION = "description";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;

        String id = request.getParameter(PARAM_NAME_ID);
        String name = request.getParameter(PARAM_NAME_NAME);
        LOG.debug("Edit: vacation name: " + name);
        String summary = request.getParameter(PARAM_NAME_SUMMARY);
        String departureDate = request.getParameter(PARAM_NAME_DEPARTURE_DATE);
        String arrivalDate = request.getParameter(PARAM_NAME_ARRIVAL_DATE);
        String destinationCityId = request.getParameter(PARAM_NAME_DESTINATION_CITY);
        String hotel = request.getParameter(PARAM_NAME_HOTEL);
        String lastMinute = request.getParameter(PARAM_NAME_LAST_MINUTE);
        LOG.debug("Last minute test: " + request.getParameter(PARAM_NAME_LAST_MINUTE));
        String price = request.getParameter(PARAM_NAME_PRICE);
        String transport = request.getParameter(PARAM_NAME_TRANSPORT);
        String services = request.getParameter(PARAM_NAME_SERVICES);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        VacationServiceImpl vacationService = new VacationServiceImpl();

        try {
            if ("application/octet-stream".equals(request.getPart("img").getContentType())){
                if (vacationService.checkEditVacation(id,name, summary, departureDate, arrivalDate, destinationCityId, hotel, lastMinute, price, transport, services, description)) {
                    page = ConfigurationManager.getProperty("path.page.admin.panel");
                }
                else {
                    request.setAttribute("errorEditVacationPassMessage",
                            TravelController.messageManager.getProperty("message.editvacationerror"));
                    page = createEditVacationPage(request, Long.parseLong(id));
                }
            }
            else {
                String SAVE_DIR = "images" + File.separator + "vacations";
                String appPath = request.getServletContext().getRealPath("");
                String savePath = appPath + SAVE_DIR;

                LOG.debug("Save Path = " + savePath);
                Part filePart = request.getPart("img");

                if (vacationService.checkEditVacation(id,name, summary, departureDate, arrivalDate, destinationCityId, hotel, lastMinute, price, transport, services, description, filePart, savePath)) {
                    page = ConfigurationManager.getProperty("path.page.admin.panel");
                }
                else {
                    request.setAttribute("errorEditVacationPassMessage",
                            TravelController.messageManager.getProperty("message.editvacationerror"));
                    page = createEditVacationPage(request, Long.parseLong(id));
                }
            }
        } catch (IOException | ServletException e) {
            throw new CommandException("Failed to get parts from request.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

    private String createEditVacationPage(HttpServletRequest request, Long id) throws CommandException {
        String page = null;
        VacationServiceImpl vacationService = new VacationServiceImpl();
        CityServiceImpl cityService = new CityServiceImpl();
        Vacation vacation = null;
        List<City> cities = null;
        try {
            vacation = vacationService.findEntityById(id);
            cities = cityService.findAllCities();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (id == vacation.getId()) {
            request.setAttribute("vacation", vacation);
            request.setAttribute("cities", cities);
            page = ConfigurationManager.getProperty("path.page.admin.edit.info.vacation");
        } else {
            page = ConfigurationManager.getProperty("path.page.admin.panel");
        }
        return page;
    }

}
