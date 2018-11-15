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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchListCommand implements ActionCommand {

    private final static Logger LOG = Logger.getLogger(SearchListCommand.class);
    
    private static final String PARAM_NAME_TOUR_TYPE = "tour_type";
    
    private static final String PARAM_NAME_TOUR_FIELD = "tour_field";
    
    private static final String PARAM_NAME_SEARCH_TEXT = "search_text";
    
    private static final String PARAM_NAME_SEARCH_TEXT_SELECT = "search_text_select";

    private static final String TOUR_TYPE_VACATION = "vacation";
    
    private static final String TOUR_TYPE_TRIP = "trip";
    
    private static final String TOUR_TYPE_SHOPPING = "shopping";

    private static final String TOUR_FIELD_NAME = "name";
    
    private static final String TOUR_FIELD_DEPARTURE_DATE = "departure_date";
    
    private static final String TOUR_FIELD_ARRIVAL_DATE = "arrival_date";
    
    private static final String TOUR_FIELD_PRICE = "price";
    
    private static final String TOUR_FIELD_TRANSPORT = "transport";

    /* (non-Javadoc)
     * @see by.bsu.travelagency.command.ActionCommand#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String tourType = request.getParameter(PARAM_NAME_TOUR_TYPE);
        String tourField = request.getParameter(PARAM_NAME_TOUR_FIELD);
        String searchText = request.getParameter(PARAM_NAME_SEARCH_TEXT);
        String searchTextSelect = request.getParameter(PARAM_NAME_SEARCH_TEXT_SELECT);

        switch (tourType){
            case TOUR_TYPE_VACATION:
                return findVacation(request, tourField, searchText, searchTextSelect);
            case TOUR_TYPE_TRIP:
                return findTrip(request, tourField, searchText, searchTextSelect);
            case TOUR_TYPE_SHOPPING:
                return findShopping(request, tourField, searchText, searchTextSelect);
            default:
                return ConfigurationManager.getProperty("path.page.main");
        }
    }

    private String findVacation(HttpServletRequest request, String tourField, String searchText, String searchTextSelect) throws CommandException {
        switch (tourField){
            case TOUR_FIELD_NAME:
                return findVacationByName(request, searchText);
            case TOUR_FIELD_DEPARTURE_DATE:
                return findVacationByDepartureDate(request, searchText);
            case TOUR_FIELD_ARRIVAL_DATE:
                return findVacationByArrivalDate(request, searchText);
            case TOUR_FIELD_PRICE:
                return findVacationByPrice(request, searchText);
            case TOUR_FIELD_TRANSPORT:
                return findVacationByTransport(request, searchTextSelect);
            default:
                return ConfigurationManager.getProperty("path.page.vacation.list");
        }
    }

    private String findTrip(HttpServletRequest request, String tourField, String searchText, String searchTextSelect) throws CommandException {
        switch (tourField){
            case TOUR_FIELD_NAME:
                return findTripByName(request, searchText);
            case TOUR_FIELD_DEPARTURE_DATE:
                return findTripByDepartureDate(request, searchText);
            case TOUR_FIELD_ARRIVAL_DATE:
                return findTripByArrivalDate(request, searchText);
            case TOUR_FIELD_PRICE:
                return findTripByPrice(request, searchText);
            case TOUR_FIELD_TRANSPORT:
                return findTripByTransport(request, searchTextSelect);
            default:
                return ConfigurationManager.getProperty("path.page.trip.list");
        }
    }

    private String findShopping(HttpServletRequest request, String tourField, String searchText, String searchTextSelect) throws CommandException {
        switch (tourField){
            case TOUR_FIELD_NAME:
                return findShoppingByName(request, searchText);
            case TOUR_FIELD_DEPARTURE_DATE:
                return findShoppingByDepartureDate(request, searchText);
            case TOUR_FIELD_ARRIVAL_DATE:
                return findShoppingByArrivalDate(request, searchText);
            case TOUR_FIELD_PRICE:
                return findShoppingByPrice(request, searchText);
            case TOUR_FIELD_TRANSPORT:
                return findShoppingByTransport(request, searchTextSelect);
            default:
                return ConfigurationManager.getProperty("path.page.shopping.list");
        }
    }

    private String findVacationByName(HttpServletRequest request, String searchText) throws CommandException {
        VacationServiceImpl vacationService = new VacationServiceImpl();
        List<Vacation> vacations;
        Date nowDate = new Date();
        try {
            vacations = vacationService.findVacationsByNameAfterNow(new java.sql.Date(nowDate.getTime()), searchText);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("vacations", vacations);
        return ConfigurationManager.getProperty("path.page.vacation.list");
    }

    private String findVacationByDepartureDate(HttpServletRequest request, String searchText) throws CommandException {
        VacationServiceImpl vacationService = new VacationServiceImpl();
        List<Vacation> vacations;
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate;
        try {
            departureDate = format.parse(searchText);
            vacations = vacationService.findVacationsByDepartureDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(departureDate.getTime()));
            request.setAttribute("vacations", vacations);
            return ConfigurationManager.getProperty("path.page.vacation.list");
        } catch (ParseException e) {
            throw new CommandException("Failed to parse date.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String findVacationByArrivalDate(HttpServletRequest request, String searchText) throws CommandException {
        VacationServiceImpl vacationService = new VacationServiceImpl();
        List<Vacation> vacations;
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date arrivalDate;
        try {
            arrivalDate = format.parse(searchText);
            vacations = vacationService.findVacationsByArrivalDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(arrivalDate.getTime()));
            request.setAttribute("vacations", vacations);
            return ConfigurationManager.getProperty("path.page.vacation.list");
        } catch (ParseException e) {
            throw new CommandException("Failed to parse date.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String findVacationByPrice(HttpServletRequest request, String searchText) throws CommandException {
        VacationServiceImpl vacationService = new VacationServiceImpl();
        List<Vacation> vacations;
        Date nowDate = new Date();
        double price = Double.parseDouble(searchText);
        try {
            vacations = vacationService.findVacationsByPriceAfterNow(new java.sql.Date(nowDate.getTime()), price);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("vacations", vacations);
        return ConfigurationManager.getProperty("path.page.vacation.list");
    }

    private String findVacationByTransport(HttpServletRequest request, String searchTextSelect) throws CommandException {
        VacationServiceImpl vacationService = new VacationServiceImpl();
        List<Vacation> vacations;
        Date nowDate = new Date();
        try {
            vacations = vacationService.findVacationsByTransportAfterNow(new java.sql.Date(nowDate.getTime()), searchTextSelect);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("vacations", vacations);
        return ConfigurationManager.getProperty("path.page.vacation.list");
    }

    private String findTripByName(HttpServletRequest request, String searchText) throws CommandException {
        TripServiceImpl tripService = new TripServiceImpl();
        List<Trip> trips;
        Date nowDate = new Date();
        try {
            trips = tripService.findTripsByNameAfterNow(new java.sql.Date(nowDate.getTime()), searchText);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("trips", trips);
        return ConfigurationManager.getProperty("path.page.trip.list");
    }

    private String findTripByDepartureDate(HttpServletRequest request, String searchText) throws CommandException {
        TripServiceImpl tripService = new TripServiceImpl();
        List<Trip> trips;
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate;
        try {
            departureDate = format.parse(searchText);
            trips = tripService.findTripsByDepartureDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(departureDate.getTime()));
            request.setAttribute("trips", trips);
            return ConfigurationManager.getProperty("path.page.trip.list");
        } catch (ParseException e) {
            throw new CommandException("Failed to parse date.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String findTripByArrivalDate(HttpServletRequest request, String searchText) throws CommandException {
        TripServiceImpl tripService = new TripServiceImpl();
        List<Trip> trips;
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date arrivalDate;
        try {
            arrivalDate = format.parse(searchText);
            trips = tripService.findTripsByArrivalDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(arrivalDate.getTime()));
            request.setAttribute("trips", trips);
            return ConfigurationManager.getProperty("path.page.trip.list");
        } catch (ParseException e) {
            throw new CommandException("Failed to parse date.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String findTripByPrice(HttpServletRequest request, String searchText) throws CommandException {
        TripServiceImpl tripService = new TripServiceImpl();
        List<Trip> trips;
        Date nowDate = new Date();
        double price = Double.parseDouble(searchText);
        try {
            trips = tripService.findTripsByPriceAfterNow(new java.sql.Date(nowDate.getTime()), price);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("trips", trips);
        return ConfigurationManager.getProperty("path.page.trip.list");
    }

    private String findTripByTransport(HttpServletRequest request, String searchTextSelect) throws CommandException {
        TripServiceImpl tripService = new TripServiceImpl();
        List<Trip> trips;
        Date nowDate = new Date();
        try {
            trips = tripService.findTripsByTransportAfterNow(new java.sql.Date(nowDate.getTime()), searchTextSelect);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("trips", trips);
        return ConfigurationManager.getProperty("path.page.trip.list");
    }

    private String findShoppingByName(HttpServletRequest request, String searchText) throws CommandException {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        List<Shopping> shoppings;
        Date nowDate = new Date();
        try {
            shoppings = shoppingService.findShoppingsByNameAfterNow(new java.sql.Date(nowDate.getTime()), searchText);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("shoppings", shoppings);
        return ConfigurationManager.getProperty("path.page.shopping.list");
    }

    private String findShoppingByDepartureDate(HttpServletRequest request, String searchText) throws CommandException {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        List<Shopping> shoppings;
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate;
        try {
            departureDate = format.parse(searchText);
            shoppings = shoppingService.findShoppingsByDepartureDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(departureDate.getTime()));
            request.setAttribute("shoppings", shoppings);
            return ConfigurationManager.getProperty("path.page.shopping.list");
        } catch (ParseException e) {
            throw new CommandException("Failed to parse date.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String findShoppingByArrivalDate(HttpServletRequest request, String searchText) throws CommandException {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        List<Shopping> shoppings;
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date arrivalDate;
        try {
            arrivalDate = format.parse(searchText);
            shoppings = shoppingService.findShoppingsByArrivalDateAfterNow(new java.sql.Date(nowDate.getTime()), new java.sql.Date(arrivalDate.getTime()));
            request.setAttribute("shoppings", shoppings);
            return ConfigurationManager.getProperty("path.page.shopping.list");
        } catch (ParseException e) {
            throw new CommandException("Failed to parse date.", e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private String findShoppingByPrice(HttpServletRequest request, String searchText) throws CommandException {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        List<Shopping> shoppings;
        Date nowDate = new Date();
        double price = Double.parseDouble(searchText);
        try {
            shoppings = shoppingService.findShoppingsByPriceAfterNow(new java.sql.Date(nowDate.getTime()), price);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("shoppings", shoppings);
        return ConfigurationManager.getProperty("path.page.shopping.list");
    }

    private String findShoppingByTransport(HttpServletRequest request, String searchTextSelect) throws CommandException {
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();
        List<Shopping> shoppings;
        Date nowDate = new Date();
        try {
            shoppings = shoppingService.findShoppingsByTransportAfterNow(new java.sql.Date(nowDate.getTime()), searchTextSelect);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute("shoppings", shoppings);
        return ConfigurationManager.getProperty("path.page.shopping.list");
    }
}
