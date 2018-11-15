package dao;


import dao.exception.DAOException;
import entity.City;

import java.util.List;

public interface CityDAO extends EntityDAO<Long, City> {
    List<City> findAllCities() throws DAOException;
}