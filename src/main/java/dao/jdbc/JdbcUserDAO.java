package dao.jdbc;

import dao.UserDAO;
import dao.exception.DAOException;
import entity.User;
import entity.UserOrderNumber;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDAO implements UserDAO {

    private final static Logger LOG = Logger.getLogger(JdbcUserDAO.class);
    
    private static final String SQL_SELECT_ALL_USERS = "SELECT id_user,login,password,role,email,name,surname,discount,balance " +
            "FROM users";
    
    private static final String SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT = "SELECT users.id_user,users.login,users.password,users.role," +
            "users.email,users.name,users.surname,users.discount,users.balance,COUNT(DISTINCT orders.id_order) AS orders FROM users " +
            "LEFT JOIN orders USING (id_user) GROUP BY users.id_user;";
    
    private static final String SQL_SELECT_USER_BY_NAME = "SELECT id_user,login,password,role,email,name,surname,discount,balance " +
            "FROM users WHERE login=?";
    
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user,login,password,role,email,name,surname,discount,balance " +
            "FROM users WHERE id_user=?";
    
    private static final String SQL_SELECT_USER_PASSWORD_BY_ID = "SELECT password FROM users WHERE id_user=?";
    
    private static final String SQL_UPDATE_USER_PASSWORD_BY_ID = "UPDATE users SET password=? WHERE id_user=?";
    
    private static final String SQL_SELECT_MONEY_BY_USER_ID = "SELECT balance FROM users WHERE id_user=?";
    
    private static final String SQL_INSERT_USER = "INSERT INTO users(login,password,role,email,name,surname,discount,balance) " +
            "VALUES(?,?,?,?,?,?,?,?)";
    
    private static final String SQL_UPDATE_USER = "UPDATE users SET login=?,password=?,role=?,email=?,name=?,surname=?,discount=?," +
            "balance=? WHERE id_user=?";
    
    private static final String SQL_UPDATE_USER_BALANCE = "UPDATE users SET balance=? WHERE id_user=?";
    
    private static final String SQL_UPDATE_USER_BALANCE_ADDITION = "UPDATE users SET balance=balance + ? WHERE id_user=?";
    
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id_user=?";

    private static final String PARAM_ID_USER = "id_user";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_ROLE = "role";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_DISCOUNT = "discount";
    private static final String PARAM_BALANCE = "balance";
    private static final String PARAM_ORDERS = "orders";


    private JdbcUserDAO() {
    }


    private static class JdbcUserDAOHolder {
        private static final JdbcUserDAO HOLDER_INSTANCE = new JdbcUserDAO();
    }



    public static JdbcUserDAO getInstance() {
        return JdbcUserDAOHolder.HOLDER_INSTANCE;
    }


    public List<User> findAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return users;
    }


    public List<UserOrderNumber> findAllUsersWithOrderCount() throws DAOException {
        List<UserOrderNumber> users = new ArrayList<>();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS_WITH_ORDER_COUNT);
            while (resultSet.next()) {
                UserOrderNumber userOrderNumber = new UserOrderNumber();
                userOrderNumber.setId(resultSet.getLong(PARAM_ID_USER));
                userOrderNumber.setLogin(resultSet.getString(PARAM_LOGIN));
                userOrderNumber.setPassword(resultSet.getString(PARAM_PASSWORD));
                userOrderNumber.setRole(resultSet.getInt(PARAM_ROLE));
                userOrderNumber.setEmail(resultSet.getString(PARAM_EMAIL));
                userOrderNumber.setName(resultSet.getString(PARAM_NAME));
                userOrderNumber.setSurname(resultSet.getString(PARAM_SURNAME));
                userOrderNumber.setDiscount(resultSet.getDouble(PARAM_DISCOUNT));
                userOrderNumber.setMoney(resultSet.getInt(PARAM_BALANCE));
                userOrderNumber.setOrderNumber(resultSet.getInt(PARAM_ORDERS));
                users.add(userOrderNumber);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return users;
    }


    public User findUserByName(String name) throws DAOException {
        User user = new User();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_BY_NAME)) {
            ps.setString(1,name);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                LOG.debug(resultSet.getString(PARAM_LOGIN));
                LOG.debug(resultSet.getInt(PARAM_ROLE));
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return user;
    }


    public double findMoneyByUserId(Long id) throws DAOException {
        double money = 0;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_MONEY_BY_USER_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                money = resultSet.getDouble(PARAM_BALANCE);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return money;
    }


    public String findPasswordByUserId(Long id) throws DAOException {
        String password = null;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_PASSWORD_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString(PARAM_PASSWORD);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return password;
    }


    public boolean updatePasswordByUserId(Long id, String password) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_USER_PASSWORD_BY_ID)) {
            ps.setString(1,password);
            ps.setLong(2,id);
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }


    @Override
    public boolean create(User user) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_USER)) {
            setUserPreparedStatement(user, ps);
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }


    @Override
    public boolean update(User user) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_USER)) {
            setUserPreparedStatement(user, ps);
            ps.setLong(9,user.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }


    public boolean updateUserBalance(Long id, double money) throws DAOException {
        return updateUserBalanceWithQuery(id, money, SQL_UPDATE_USER_BALANCE);
    }


    public boolean updateUserBalanceAddition(Long id, double moneyToAdd) throws DAOException {
        return updateUserBalanceWithQuery(id, moneyToAdd, SQL_UPDATE_USER_BALANCE_ADDITION);
    }


    @Override
    public User findEntityById(Long id) throws DAOException {
        User user = new User();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_USER)) {
            ps.setLong(1,id);
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    private boolean updateUserBalanceWithQuery(Long id, double money, String query) throws DAOException{
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDouble(1,money);
            ps.setLong(2,id);
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    private void setUserPreparedStatement(User user, PreparedStatement ps) throws DAOException {
        try {
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getRole());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getName());
            ps.setString(6,user.getSurname());
            ps.setDouble(7,user.getDiscount());
            ps.setDouble(8,user.getMoney());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    private User createUser(ResultSet resultSet) throws DAOException {
        try {
            User user = new User();
            user.setId(resultSet.getLong(PARAM_ID_USER));
            user.setLogin(resultSet.getString(PARAM_LOGIN));
            user.setPassword(resultSet.getString(PARAM_PASSWORD));
            user.setRole(resultSet.getInt(PARAM_ROLE));
            user.setEmail(resultSet.getString(PARAM_EMAIL));
            user.setName(resultSet.getString(PARAM_NAME));
            user.setSurname(resultSet.getString(PARAM_SURNAME));
            user.setDiscount(resultSet.getDouble(PARAM_DISCOUNT));
            user.setMoney(resultSet.getInt(PARAM_BALANCE));
            return user;
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }
}
