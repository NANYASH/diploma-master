package service;

import entity.City;
import service.exception.ServiceException;

import java.util.List;

public interface CityService {

    List<City> findAllCities() throws ServiceException;


    boolean checkCreateCity(String enterName, String enterCountryId) throws ServiceException;

    boolean checkEditCity(String enterId, String enterName, String enterCountryId) throws ServiceException;


    boolean delete(Long id) throws ServiceException;


    City findEntityById(Long id) throws ServiceException;

}
