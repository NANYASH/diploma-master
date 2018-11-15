package command.shopping;

import command.ActionCommand;
import command.exception.CommandException;
import controller.TravelController;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.ShoppingServiceImpl;
import org.apache.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


public class CreateShoppingCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(CreateShoppingCommand.class);

    private static final String PARAM_NAME_NAME = "name";

    private static final String PARAM_NAME_SUMMARY = "summary";

    private static final String PARAM_NAME_DEPARTURE_DATE = "departure-date";

    private static final String PARAM_NAME_ARRIVAL_DATE = "arrival-date";

    private static final String PARAM_NAME_DESTINATION_CITY = "destination-city";

    private static final String PARAM_NAME_SHOPS = "shops";

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

        String name = request.getParameter(PARAM_NAME_NAME);
        String summary = request.getParameter(PARAM_NAME_SUMMARY);
        String departureDate = request.getParameter(PARAM_NAME_DEPARTURE_DATE);
        String arrivalDate = request.getParameter(PARAM_NAME_ARRIVAL_DATE);
        String destinationCityId = request.getParameter(PARAM_NAME_DESTINATION_CITY);
        String shops = request.getParameter(PARAM_NAME_SHOPS);
        String lastMinute = request.getParameter(PARAM_NAME_LAST_MINUTE);
        LOG.debug("Last minute test: " + request.getParameter(PARAM_NAME_LAST_MINUTE));
        String price = request.getParameter(PARAM_NAME_PRICE);
        String transport = request.getParameter(PARAM_NAME_TRANSPORT);
        String services = request.getParameter(PARAM_NAME_SERVICES);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);

        String SAVE_DIR = "images" + File.separator + "shoppings";
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + SAVE_DIR;

        LOG.debug("Save Path = " + savePath);
        Part filePart = null;
        try {
            filePart = request.getPart("img");

            ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();

            if (shoppingService.checkCreateShopping(name, summary, departureDate, arrivalDate, destinationCityId, shops, lastMinute, price, transport, services, description, filePart, savePath)) {
                page = ConfigurationManager.getProperty("path.page.admin.panel");
            }
            else {
                request.setAttribute("errorCreateShoppingPassMessage",
                        TravelController.messageManager.getProperty("message.createshoppingerror"));
                page = ConfigurationManager.getProperty("path.page.admin.create.shopping");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException | ServletException e) {
            throw new CommandException("Failed to get parts from request.",e);
        }

        return page;
    }

}
