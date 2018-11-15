package service.impl;

import dao.exception.DAOException;
import dao.jdbc.JdbcCityDAO;
import dao.jdbc.JdbcShoppingDAO;
import entity.City;
import entity.Shopping;
import entity.Transport;
import service.ShoppingService;
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


public class ShoppingServiceImpl implements ShoppingService {

    private final static Logger LOG = Logger.getLogger(ShoppingServiceImpl.class);

    private final static int SHOPPING_ID_FOR_INSERT = 0;

    private final static String REGEX_FILE_NAME = "([0-9])*";


    @Override
    public boolean checkCreateShopping(String enterName, String enterSummary, String enterDepartureDate,
                                              String enterArrivalDate, String enterDestinationCityId,
                                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                              String enterServices, String enterDescription, Part img, String savePath) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(SHOPPING_ID_FOR_INSERT);
            shopping.setName(enterName);
            shopping.setSummary(enterSummary);
            shopping.setDescription(enterDescription);
            try {
                shopping.setDepartureDate(format.parse(enterDepartureDate));
                shopping.setArrivalDate(format.parse(enterArrivalDate));

                shopping.setPrice(Double.parseDouble(enterPrice));
                //shopping.setLastMinute(("on".equals(enterLastMinute)));
                shopping.setShops(enterShops);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                shopping.setServices(enterServices);

                JdbcShoppingDAO shoppingDAO = JdbcShoppingDAO.getInstance();
                Long lastId = null;
                lastId = shoppingDAO.findLastShoppingId() + 1L;

                img.write(savePath + File.separator + lastId + ".jpg");

                shopping.setPathImage("/images/shoppings/"+ lastId + ".jpg");

                if (shoppingDAO.create(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create shopping.", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Shopping).", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Shopping).", e);
            }
        }
        return flag;
    }


    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCityId,
                                            String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription, Part img, String savePath) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateString(enterDepartureDate) && Validator.validateString(enterArrivalDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(Integer.parseInt(enterId));
            shopping.setName(enterName);
            shopping.setSummary(enterSummary);
            shopping.setDescription(enterDescription);
            try {
                shopping.setDepartureDate(format.parse(enterDepartureDate));
                shopping.setArrivalDate(format.parse(enterArrivalDate));
                shopping.setPrice(Double.parseDouble(enterPrice));
                //shopping.setLastMinute(("on".equals(enterLastMinute)));
                shopping.setShops(enterShops);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                shopping.setServices(enterServices);

                JdbcShoppingDAO shoppingDAO = JdbcShoppingDAO.getInstance();
                String pathImage = null;
                pathImage = shoppingDAO.findPathImageShoppingById(shopping.getId());
                Pattern patternFileName = Pattern.compile(REGEX_FILE_NAME);
                Matcher matcherFileName = patternFileName.matcher(pathImage);
                String fileName = null;

                while (matcherFileName.find()){
                    if (!matcherFileName.group().isEmpty()){
                        fileName = matcherFileName.group();
                        LOG.info("Logic: fileName: " + fileName);
                    }
                }

                LOG.debug("Logic: pathImage: " + "/images/shoppings/"+ fileName + ".jpg");
                img.write(savePath + File.separator + fileName + ".jpg");

                shopping.setPathImage("/images/shoppings/"+ fileName + ".jpg");

                if (shoppingDAO.update(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update shopping.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Shopping).", e);
            } catch (IOException e) {
                throw new ServiceException("Failed to write to file (Shopping).", e);
            }
        }
        return flag;
    }


    public boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                                            String enterArrivalDate, String enterDestinationCityId,
                                            String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                            String enterServices, String enterDescription) throws ServiceException {
        boolean flag = false;
        if (Validator.validateNameTour(enterName) && Validator.validateSummary(enterSummary) && Validator.validatePrice(enterPrice) && Validator.validateString(enterDepartureDate) && Validator.validateString(enterArrivalDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Shopping shopping = new Shopping();
            shopping.setId(Integer.parseInt(enterId));
            shopping.setName(enterName);
            shopping.setSummary(enterSummary);
            shopping.setDescription(enterDescription);
            try {
                shopping.setDepartureDate(format.parse(enterDepartureDate));
                shopping.setArrivalDate(format.parse(enterArrivalDate));

                shopping.setPrice(Double.parseDouble(enterPrice));
                //shopping.setLastMinute(("on".equals(enterLastMinute)));
                shopping.setShops(enterShops);
                JdbcCityDAO cityDAO = JdbcCityDAO.getInstance();
                ArrayList<City> cities = new ArrayList<City>();
                City city = cityDAO.findEntityById(Long.parseLong(enterDestinationCityId));
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(enterTransport.toUpperCase()));
                shopping.setServices(enterServices);

                JdbcShoppingDAO shoppingDAO = JdbcShoppingDAO.getInstance();
                String pathImage = null;

                pathImage = shoppingDAO.findPathImageShoppingById(shopping.getId());

                LOG.debug("Logic: pathImage: " + pathImage);

                shopping.setPathImage(pathImage);

                if (shoppingDAO.update(shopping)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to update shopping.", e);
            } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Shopping).", e);
            }
        }
        return flag;
    }


    @Override
    public Shopping findEntityById(Long id) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findAllShoppingsAfterNow(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findAllSortShoppingsAfterNow(nowDate, criterion, order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> selectLastShoppings(Date nowDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().selectLastShoppings(nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByNameAfterNow(nowDate, name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByDepartureDateAfterNow(nowDate, departureDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByArrivalDateAfterNow(nowDate, arrivalDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByPriceAfterNow(nowDate, price);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws ServiceException {
        try {
            return JdbcShoppingDAO.getInstance().findShoppingsByTransportAfterNow(nowDate, transport);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
