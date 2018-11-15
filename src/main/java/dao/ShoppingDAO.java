package dao;


import dao.exception.DAOException;
import entity.Shopping;

import java.sql.Date;
import java.util.List;

public interface ShoppingDAO extends EntityDAO<Long, Shopping> {


    List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws DAOException;

    List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException;

    List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws DAOException;

    List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException;


    List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException;


    List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws DAOException;

    List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws DAOException;

    Long findLastShoppingId() throws DAOException;

    String findPathImageShoppingById(Long id) throws DAOException;

    List<Shopping> selectLastShoppings(Date nowDate) throws DAOException;
}
