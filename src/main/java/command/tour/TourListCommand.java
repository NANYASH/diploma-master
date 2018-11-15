package command.tour;

import command.ActionCommand;
import command.exception.CommandException;
import entity.Shopping;
import entity.Trip;
import entity.Vacation;
import resource.ConfigurationManager;
import service.exception.ServiceException;
import service.impl.ShoppingServiceImpl;
import service.impl.TripServiceImpl;
import service.impl.VacationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class TourListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(TourListCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        Date nowDate = new Date();
        VacationServiceImpl vacationService = new VacationServiceImpl();
        TripServiceImpl tripService = new TripServiceImpl();
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();

        try {
        List<Vacation> vacations = vacationService.selectLastVacations(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("vacations", vacations);

        List<Trip> trips = tripService.selectLastTrips(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("trips", trips);

        List<Shopping> shoppings = shoppingService.selectLastShoppings(new java.sql.Date(nowDate.getTime()));
        request.setAttribute("shoppings", shoppings);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        page = ConfigurationManager.getProperty("path.page.main");
        return page;
    }
}
