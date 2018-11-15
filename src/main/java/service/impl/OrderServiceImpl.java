package service.impl;

import dao.exception.DAOException;
import dao.jdbc.JdbcOrderDAO;
import dao.jdbc.JdbcUserDAO;
import entity.Order;
import entity.OrderTourInfo;
import entity.TourType;
import service.OrderService;
import service.exception.ServiceException;
import util.Validator;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class OrderServiceImpl implements OrderService {

    private final static Logger LOG = Logger.getLogger(OrderServiceImpl.class);

    private final static Long ORDER_ID_FOR_INSERT = 0L;


    @Override
    public List<OrderTourInfo> findAllUserOrdersByUserIdAfterNow(Long userId, Date nowDate) throws ServiceException {
        try {
            return JdbcOrderDAO.getInstance().findAllUserOrdersByUserIdAfterNow(userId, nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    public boolean checkOrder(Long userId, Long tourId, String departureDate, String arrivalDate, int enterQuantity,
                                     String tourType, double totalPrice, double userBalance) throws ServiceException {
        boolean flag = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date departureDateFormatted = null;
        try {
            departureDateFormatted = format.parse(departureDate);
        } catch (ParseException e) {
            throw new ServiceException("Failed to parse date (Order).", e);
        }
        if (Validator.validateQuantity(enterQuantity) && Validator.validateOrderDate(departureDateFormatted)) {
            Order order = new Order();
            order.setOrderId(ORDER_ID_FOR_INSERT);
            order.setUserId(userId);
            order.setTourId(tourId);
            order.setOrderDate(new java.util.Date());
            LOG.debug("Order date: " + new java.util.Date());
            try {
                //order.setDepartureDate(format.parse(departureDate));
                //order.setArrivalDate(format.parse(arrivalDate));
                order.setQuantity(enterQuantity);
                //order.setTourType(TourType.valueOf(tourType.toUpperCase()));
                order.setTotalPrice(totalPrice);
                double newBalance = userBalance - totalPrice;
                if (JdbcOrderDAO.getInstance().create(order) && JdbcUserDAO.getInstance().updateUserBalance(userId, newBalance)) {
                    flag = true;
                }
            } catch (DAOException e) {
                throw new ServiceException("Failed to create order or to update user balance (Order).", e);
           /* } catch (ParseException e) {
                throw new ServiceException("Failed to parse date (Order).", e);
            }*/
            }
        }
            return flag;
        }


    @Override
    public boolean checkOrderDelete(Long orderId, Long userId) throws ServiceException {
        boolean flag = false;
        JdbcOrderDAO orderDAO = JdbcOrderDAO.getInstance();
        JdbcUserDAO userDAO = JdbcUserDAO.getInstance();

        try {
            LOG.debug("checkOrderDelete userId = " + orderDAO.findUserIdByOrderId(orderId));
            if (userId == orderDAO.findUserIdByOrderId(orderId)) {
                java.util.Date departureDate = orderDAO.findDepartureDateById(orderId);
                int totalPrice = orderDAO.findTotalPriceById(orderId);
                if (Validator.validateOrderDate(departureDate)) {
                    if (orderDAO.delete(orderId) && userDAO.updateUserBalanceAddition(userId, totalPrice)) {
                        flag = true;
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Failed to delete order or to update user balance (Order).", e);
        }
        return flag;
    }


    @Override
    public List<OrderTourInfo> findAllUserOrdersByUserIdBeforeNow(Long userId, Date nowDate) throws ServiceException {
        try {
            return JdbcOrderDAO.getInstance().findAllUserOrdersByUserIdBeforeNow(userId, nowDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
