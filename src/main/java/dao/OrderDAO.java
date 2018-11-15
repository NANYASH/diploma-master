package dao;


import dao.exception.DAOException;
import entity.Order;
import entity.OrderTourInfo;

import java.sql.Date;
import java.util.List;

public interface OrderDAO extends EntityDAO<Long, Order> {

    List<OrderTourInfo> findAllUserOrdersByUserId(Long userId) throws DAOException;

    List<OrderTourInfo> findAllUserOrdersByUserIdAfterNow(Long userId, Date nowDate) throws DAOException;

    List<OrderTourInfo> findAllUserOrdersByUserIdBeforeNow(Long userId, Date nowDate) throws DAOException;

    Date findDepartureDateById(Long id) throws DAOException;

    int findTotalPriceById(Long id) throws DAOException;

    long findUserIdByOrderId(Long id) throws DAOException;
}