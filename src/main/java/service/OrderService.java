package service;

import entity.OrderTourInfo;
import service.exception.ServiceException;

import java.sql.Date;
import java.util.List;


public interface OrderService {

    List<OrderTourInfo> findAllUserOrdersByUserIdAfterNow(Long userId, Date nowDate) throws ServiceException;

    boolean checkOrder(Long userId, Long tourId, String departureDate, String arrivalDate, int enterQuantity, String tourType, double totalPrice, double userBalance) throws ServiceException;


    boolean checkOrderDelete(Long orderId, Long userId) throws ServiceException;


    List<OrderTourInfo> findAllUserOrdersByUserIdBeforeNow(Long userId, Date nowDate) throws ServiceException;
}
