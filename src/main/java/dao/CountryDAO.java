package dao;


import dao.exception.DAOException;
import entity.Country;

import java.util.List;

public interface CountryDAO extends EntityDAO<Long, Country> {

    List<Country> findAllCountries() throws DAOException;
}