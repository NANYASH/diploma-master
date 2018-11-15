package service.impl;

import dao.exception.DAOException;
import dao.jdbc.JdbcTripDAO;
import entity.City;
import entity.Transport;
import entity.Trip;
import service.TripService;
import service.exception.ServiceException;
import util.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TripServiceImpl implements TripService {

    private final static Logger LOG = Logger.getLogger(TripServiceImpl.class);

    private final static int TRIP_ID_FOR_INSERT = 0;

    private final static String REGEX_FILE_NAME = "([0-9])*";

    @Override
    public boolean checkCreateTrip(String enterName, String enterSummary, String enterDepartureDate,
                                          String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                                          String enterServices, String enterDescription, Part img, String savePath, ArrayList<City> cities) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Trip trip = new Trip();
            trip.setId(TRIP_ID_FOR_INSERT);
            trip.setName(enterName);
            trip.setSummary(enterSummary);
            trip.setDescription(enterDescription);
            try {
                trip.setDepartureDate(format.parse(enterDepartureDate));
                trip.setArrivalDate(format.parse(enterArrivalDate));
                trip.setPrice(Double.parseDouble(enterPrice));
                //trip.setLastMinute(("on".equals(enterLastMinute)));
                trip.setAttractions(enterAttractions);
                trip.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                trip.setServices(enterServices);
                trip.setCities(cities);

                JdbcTripDAO tripDAO = JdbcTripDAO.getInstance();
                Long lastId = null;
                lastId = tripDAO.findLastTripId() + 1L;

                img.write(savePath + File.separator + lastId + ".jpg");

                trip.setPathImage("/images/trips/"+ lastId + ".jpg");

                if (tripDAO.create(trip)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create trip.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Trip).", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Trip).", e);
            }
        }
        return flag;
    }


    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkEditTrip(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                        String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                                        String enterServices, String enterDescription, Part img, String savePath, ArrayList<City> cities) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateString(enterDepartureDate) && Validator.validateString(enterArrivalDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Trip trip = new Trip();
            trip.setId(Integer.parseInt(enterId));
            trip.setName(enterName);
            trip.setSummary(enterSummary);
            trip.setDescription(enterDescription);
            try {
                trip.setDepartureDate(format.parse(enterDepartureDate));
                trip.setArrivalDate(format.parse(enterArrivalDate));

                trip.setPrice(Double.parseDouble(enterPrice));
                //trip.setLastMinute(("on".equals(enterLastMinute)));
                trip.setAttractions(enterAttractions);
                trip.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                trip.setServices(enterServices);
                trip.setCities(cities);

                JdbcTripDAO tripDAO = JdbcTripDAO.getInstance();
                String pathImage = tripDAO.findPathImageTripById(trip.getId());

                Pattern patternFileName = Pattern.compile(REGEX_FILE_NAME);
                Matcher matcherFileName = patternFileName.matcher(pathImage);
                String fileName = null;

                while (matcherFileName.find()){
                    if (!matcherFileName.group().isEmpty()){
                        fileName = matcherFileName.group();
                        LOG.info("Logic: fileName: " + fileName);
                    }
                }

                LOG.debug("Logic: pathImage: " + "/images/trips/"+ fileName + ".jpg");

                img.write(savePath + File.separator + fileName + ".jpg");
                trip.setPathImage("/images/trips/"+ fileName + ".jpg");

                if (tripDAO.update(trip)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update trip.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Shopping).", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Trip).", e);
            }
        }
        return flag;
    }


    public boolean checkEditTrip(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                        String enterArrivalDate, String enterAttractions, String enterLastMinute, String enterPrice, String enterTransport,
                                        String enterServices, String enterDescription, ArrayList<City> cities) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateString(enterDepartureDate) && Validator.validateString(enterArrivalDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Trip trip = new Trip();
            trip.setId(Integer.parseInt(enterId));
            trip.setName(enterName);
            trip.setSummary(enterSummary);
            trip.setDescription(enterDescription);
            try {
                trip.setDepartureDate(format.parse(enterDepartureDate));
                trip.setArrivalDate(format.parse(enterArrivalDate));
                trip.setPrice(Double.parseDouble(enterPrice));
                //trip.setLastMinute(("on".equals(enterLastMinute)));
                trip.setAttractions(enterAttractions);
                trip.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                trip.setServices(enterServices);
                trip.setCities(cities);

                JdbcTripDAO tripDAO = JdbcTripDAO.getInstance();
                String pathImage = tripDAO.findPathImageTripById(trip.getId());

                LOG.debug("Logic: pathImage: " + pathImage);

                trip.setPathImage(pathImage);

                if (tripDAO.update(trip)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update trip.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Trip).", e);
            }
        }
        return flag;
    }


    @Override
    public Trip findEntityById(Long id) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> findAllTripsAfterNow(Date nowDate) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findAllTripsAfterNow(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> findAllSortTripsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findAllSortTripsAfterNow(nowDate, criterion, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> selectLastTrips(Date nowDate) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().selectLastTrips(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> findTripsByNameAfterNow(Date nowDate, String name) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findTripsByNameAfterNow(nowDate, name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> findTripsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findTripsByDepartureDateAfterNow(nowDate, departureDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> findTripsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findTripsByArrivalDateAfterNow(nowDate, arrivalDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> findTripsByPriceAfterNow(Date nowDate, double price) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findTripsByPriceAfterNow(nowDate, price);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Trip> findTripsByTransportAfterNow(Date nowDate, String transport) throws ServiceException {
        try {
            return JdbcTripDAO.getInstance().findTripsByTransportAfterNow(nowDate, transport);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
