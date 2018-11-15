package service;

import entity.Country;
import service.exception.ServiceException;


import java.util.List;

public interface CountryService {

    List<Country> findAllCountries() throws ServiceException;

    boolean checkCreateCountry(String countryName) throws ServiceException;

    boolean checkEditCountry(String countryId, String countryName) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    Country findEntityById(Long id) throws ServiceException;
}
