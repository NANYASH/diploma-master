package service;

import entity.City;
import entity.Trip;
import service.exception.ServiceException;


import javax.servlet.http.Part;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public interface TripService {


    boolean checkCreateTrip(String enterName, String enterSummary, String enterDepartureDate,
                            String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                            String enterServices, String enterDescription, Part img, String savePath, ArrayList<City> cities) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    boolean checkEditTrip(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                          String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                          String enterServices, String enterDescription, Part img, String savePath, ArrayList<City> cities) throws ServiceException;


    Trip findEntityById(Long id) throws ServiceException;


    List<Trip> findAllTripsAfterNow(Date nowDate) throws ServiceException;


    List<Trip> findAllSortTripsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException;


    List<Trip> selectLastTrips(Date nowDate) throws ServiceException;


    List<Trip> findTripsByNameAfterNow(Date nowDate, String name) throws ServiceException;


    List<Trip> findTripsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException;


    List<Trip> findTripsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException;


    List<Trip> findTripsByPriceAfterNow(Date nowDate, double price) throws ServiceException;


    List<Trip> findTripsByTransportAfterNow(Date nowDate, String transport) throws ServiceException;
}
