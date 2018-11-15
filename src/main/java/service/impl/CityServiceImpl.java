package service.impl;

import dao.exception.DAOException;
import dao.jdbc.JdbcCityDAO;
import entity.City;
import entity.Country;
import service.CityService;
import service.exception.ServiceException;
import util.Validator;
import org.apache.log4j.Logger;

import java.util.List;


public class CityServiceImpl implements CityService {

    private final static Logger LOG = Logger.getLogger(CityServiceImpl.class);

    private final static int CITY_ID_FOR_INSERT = 0;


    @Override
    public List<City> findAllCities() throws ServiceException {
        try {
            return JdbcCityDAO.getInstance().findAllCities();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean checkCreateCity(String enterName, String enterCountryId) throws ServiceException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                City city = new City();
                city.setId(CITY_ID_FOR_INSERT);
                city.setName(enterName);
                Country country = new Country();
                country.setId(Long.parseLong(enterCountryId));
                city.setCountry(country);

                if (JdbcCityDAO.getInstance().create(city)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create city.", e);
            }
        }
        return flag;
    }


    @Override
    public boolean checkEditCity(String enterId, String enterName, String enterCountryId) throws ServiceException {
        boolean flag = false;
        if (Validator.validateCountryAndCityName(enterName)){
            try {
                City city = new City();
                city.setId(Long.parseLong(enterId));
                city.setName(enterName);
                Country country = new Country();
                country.setId(Long.parseLong(enterCountryId));
                city.setCountry(country);

                if (JdbcCityDAO.getInstance().update(city)){
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create city.", e);
            }
        }
        return flag;
    }


    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return JdbcCityDAO.getInstance().delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public City findEntityById(Long id) throws ServiceException {
        try {
            return JdbcCityDAO.getInstance().findEntityById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
