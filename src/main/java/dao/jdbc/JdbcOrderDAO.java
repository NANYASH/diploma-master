package dao.jdbc;

import dao.OrderDAO;
import dao.exception.DAOException;
import entity.Order;
import entity.OrderTourInfo;
import entity.TourType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderDAO implements OrderDAO {

    private static final Logger LOG = Logger.getLogger(JdbcOrderDAO.class);

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders(id_user,id_tour,quantity,total_price,order_date) VALUES(?,?,?,?,?); " +
            "INSERT INTO tours(departure_date,arrival_date) VALUES (?,?);";

    private static final String SQL_SELECT_ALL_ORDERS_BY_USER_ID = "SELECT orders.id_order,orders.id_user,orders.id_tour,orders.quantity,orders.total_price,orders.order_date," +
            "tours.departure_date,tours.arrival_date,tour_type.name as tour_type,tours.name " +
            "FROM orders JOIN tours USING (id_tour) JOIN tour_type USING (id_type)" +
            "WHERE orders.id_user=? AND tours.id_type IN (1,2,3)" +
            "ORDER BY orders.id_order DESC;";//+

    private static final String SQL_SELECT_ALL_ORDERS_BY_USER_ID_AFTER_NOW = "SELECT id_order,orders.id_user,orders.id_tour,orders.quantity,orders.total_price,orders.order_date," +
            "tours.departure_date,tours.arrival_date,tour_type.name AS tour_type,tours.name " +
            "FROM orders JOIN tours USING (id_tour) JOIN tour_type USING (id_type)" +
            "WHERE orders.id_user=? AND tours.departure_date>?" +
            "AND tours.id_type IN (1,2,3) ORDER BY orders.id_order DESC;";

    private static final String SQL_SELECT_ALL_ORDERS_BY_USER_ID_BEFORE_NOW = "SELECT orders.id_order,orders.id_user,orders.id_tour,orders.quantity,orders.total_price,orders.order_date,tours.departure_date,tours.arrival_date,tour_type.name as tour_type,tours.name " +
            "FROM orders JOIN tours USING (id_tour) JOIN (tour_type) USING (id_type)" +
            "WHERE orders.id_user=? AND tours.departure_date<=? AND tours.id_type IN (1,2,3)" +
            "ORDER BY orders.id_order DESC;";

    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id_order=?";
    
    private static final String SQL_SELECT_DEPARTURE_DATE_BY_ID = "SELECT departure_date FROM orders WHERE id_order=?";
    
    private static final String SQL_SELECT_TOTAL_PRICE_BY_ID = "SELECT total_price FROM orders WHERE id_order=?";

    private static final String SQL_SELECT_USER_ID_BY_ORDER_ID = "SELECT id_user FROM orders WHERE id_order=?";

    private static final String PARAM_ID_ORDER = "id_order";
    private static final String PARAM_ID_USER = "id_user";
    private static final String PARAM_ID_TOUR = "id_tour";
    private static final String PARAM_QUANTITY = "quantity";
    private static final String PARAM_TOTAL_PRICE = "total_price";
    private static final String PARAM_ORDER_DATE = "order_date";
    private static final String PARAM_DEPARTURE_DATE = "departure_date";
    private static final String PARAM_ARRIVAL_DATE = "arrival_date";
    private static final String PARAM_TOUR_TYPE = "tour_type";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_PATH_IMAGE = "path_image";

    
    private JdbcOrderDAO() {
    }
    
    private static class JdbcOrderDAOHolder {
        private static final JdbcOrderDAO HOLDER_INSTANCE = new JdbcOrderDAO();
    }

    
    public static JdbcOrderDAO getInstance() {
        return JdbcOrderDAOHolder.HOLDER_INSTANCE;
    }

    
    public List<OrderTourInfo> findAllUserOrdersByUserId(Long userId) throws DAOException {
        List<OrderTourInfo> orderTourInfos = new ArrayList<>();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_USER_ID)) {
            ps.setLong(1,userId);
            ResultSet resultSet = ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return orderTourInfos;
    }
    
    public List<OrderTourInfo> findAllUserOrdersByUserIdAfterNow(Long userId, Date nowDate) throws DAOException {
        return findAllUserOrdersByDateWithQuery(userId, nowDate, SQL_SELECT_ALL_ORDERS_BY_USER_ID_AFTER_NOW);
    }
    
    public List<OrderTourInfo> findAllUserOrdersByUserIdBeforeNow(Long userId, Date nowDate) throws DAOException {
        return findAllUserOrdersByDateWithQuery(userId, nowDate, SQL_SELECT_ALL_ORDERS_BY_USER_ID_BEFORE_NOW);
    }
    
    public Date findDepartureDateById(Long id) throws DAOException {
        Date departureDate = null;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_DEPARTURE_DATE_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                departureDate = resultSet.getDate(PARAM_DEPARTURE_DATE);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return departureDate;
    }
    
    public int findTotalPriceById(Long id) throws DAOException {
        int totalPrice = 0;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TOTAL_PRICE_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                totalPrice = resultSet.getInt(PARAM_TOTAL_PRICE);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return totalPrice;
    }
    
    public long findUserIdByOrderId(Long id) throws DAOException {
        long userId = 0;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_ID_BY_ORDER_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong(PARAM_ID_USER);
                LOG.debug("userId from DAO.findUserIdByOrderId = " + userId);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return userId;
    }
    
    @Override
    public boolean create(Order order) throws DAOException {
        boolean flag = false;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_ORDER)) {
            ps.setLong(1,order.getUserId());
            ps.setLong(2,order.getTourId());
            ps.setInt(3,order.getQuantity());
            ps.setDouble(4,order.getTotalPrice());
            ps.setDate(5,new Date(order.getOrderDate().getTime()));
            ps.setDate(6,new Date(order.getTour().getDepartureDate().getTime()));
            ps.setDate(7,new Date(order.getTour().getArrivalDate().getTime()));
            if (ps.executeUpdate() != 0){
                flag = true;
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }
    
    @Override
    public Order findEntityById(Long id) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = false;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_ORDER)) {
            ps.setLong(1,id);
            if (ps.executeUpdate() != 0){
                flag = true;
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        LOG.debug("deleteOrder = " + flag);
        return flag;
    }

    @Override
    public boolean update(Order entity) {
        throw new UnsupportedOperationException();
    }

    private void createOrderTourInfos (ResultSet resultSet, List<OrderTourInfo> orderTourInfos) throws SQLException {
        while (resultSet.next()) {
            OrderTourInfo orderTourInfo = new OrderTourInfo();
            orderTourInfo.setOrderId(resultSet.getLong(PARAM_ID_ORDER));
            orderTourInfo.setUserId(resultSet.getLong(PARAM_ID_USER));
            orderTourInfo.setTourId(resultSet.getLong(PARAM_ID_TOUR));
            orderTourInfo.setQuantity(resultSet.getInt(PARAM_QUANTITY));
            orderTourInfo.setTotalPrice(resultSet.getInt(PARAM_TOTAL_PRICE));
            orderTourInfo.setOrderDate(resultSet.getDate(PARAM_ORDER_DATE));
            orderTourInfo.getTour().setDepartureDate(resultSet.getDate(PARAM_DEPARTURE_DATE));
            orderTourInfo.getTour().setArrivalDate(resultSet.getDate(PARAM_ARRIVAL_DATE));
            orderTourInfo.getTour().setTourType(TourType.valueOf(resultSet.getString(PARAM_TOUR_TYPE)));
            orderTourInfo.setName(resultSet.getString(PARAM_NAME));
            orderTourInfo.setPathImage(resultSet.getString(PARAM_PATH_IMAGE));
            orderTourInfos.add(orderTourInfo);
        }
    }
    
    private List<OrderTourInfo> findAllUserOrdersByDateWithQuery(Long userId, Date nowDate, String query) throws DAOException {
        List<OrderTourInfo> orderTourInfos = new ArrayList<>();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setLong(1,userId);
            ps.setDate(2,nowDate);
            ResultSet resultSet =
                    ps.executeQuery();
            createOrderTourInfos(resultSet, orderTourInfos);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return orderTourInfos;
    }
}
