package service;

import entity.Shopping;
import service.exception.ServiceException;


import javax.servlet.http.Part;
import java.sql.Date;
import java.util.List;


public interface ShoppingService {


    boolean checkCreateShopping(String enterName, String enterSummary, String enterDepartureDate,
                                String enterArrivalDate, String enterDestinationCityId,
                                String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                                String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;

    boolean delete(Long id) throws ServiceException;

    boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;

    boolean checkEditShopping(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterShops, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription) throws ServiceException;

    Shopping findEntityById(Long id) throws ServiceException;


    List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws ServiceException;


    List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException;


    List<Shopping> selectLastShoppings(Date nowDate) throws ServiceException;


    List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws ServiceException;


    List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException;


    List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException;


    List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws ServiceException;


    List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws ServiceException;
}
