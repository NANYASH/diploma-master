package service;

import entity.Vacation;
import service.exception.ServiceException;


import javax.servlet.http.Part;
import java.sql.Date;
import java.util.List;


public interface VacationService {

    boolean checkCreateVacation(String enterName, String enterSummary, String enterDepartureDate,
                                String enterArrivalDate, String enterDestinationCityId,
                                String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                                String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;


    boolean delete(Long id) throws ServiceException;


    boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription, Part img, String savePath) throws ServiceException;


    boolean checkEditVacation(String enterId, String enterName, String enterSummary, String enterDepartureDate,
                              String enterArrivalDate, String enterDestinationCityId,
                              String enterHotel, String enterLastMinute, String enterPrice, String enterTransport,
                              String enterServices, String enterDescription) throws ServiceException;


    Vacation findEntityById(Long id) throws ServiceException;


    List<Vacation> findAllVacationsAfterNow(Date nowDate) throws ServiceException;

    List<Vacation> findAllSortVacationsAfterNow(Date nowDate, String criterion, boolean order) throws ServiceException;


    List<Vacation> selectLastVacations(Date nowDate) throws ServiceException;

    List<Vacation> findVacationsByNameAfterNow(Date nowDate, String name) throws ServiceException;

    List<Vacation> findVacationsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws ServiceException;


    List<Vacation> findVacationsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws ServiceException;

    List<Vacation> findVacationsByPriceAfterNow(Date nowDate, double price) throws ServiceException;


    List<Vacation> findVacationsByTransportAfterNow(Date nowDate, String transport) throws ServiceException;
}
