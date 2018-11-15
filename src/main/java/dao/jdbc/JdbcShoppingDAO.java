package dao.jdbc;

import dao.ShoppingDAO;
import dao.exception.DAOException;
import entity.City;
import entity.Country;
import entity.Shopping;
import entity.Transport;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcShoppingDAO implements ShoppingDAO {

    private final static Logger LOG = Logger.getLogger(JdbcShoppingDAO.class);
    
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3";

    private static final String SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND tours.name LIKE ? AND id_type=3";
    
    private static final String SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND departure_date=? AND id_type=3";
    
    private static final String SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND arrival_date=? AND id_type=3";
    
    private static final String SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND price=? AND id_type=3";
    
    private static final String SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? " +
            "AND id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?) AND id_type=3";
    
    private static final String SQL_SELECT_SHOPPING_BY_ID = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date," +
            "price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, " +
            "countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours " +
            "JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) " +
            "JOIN countries USING (id_country) WHERE id_tour=? AND id_type=3";

    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY tours.name";
    
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT id_tour,tours.name,summary," +
            "description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY departure_date";
    
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT id_tour,tours.name,summary," +
            "description,departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY arrival_date";
    
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY price";
    
    private static final String SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY transport";

    private static final String SQL_SELECT_LAST_SHOPPINGS = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date," +
            "price,hot_tour,shops,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, " +
            "countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours " +
            "JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) " +
            "JOIN countries USING (id_country) WHERE departure_date>? AND id_type=3 ORDER BY id_tour DESC LIMIT 6";
    
    private static final String SQL_SELECT_LAST_SHOPPING_ID = "SELECT id_tour FROM tours WHERE id_type=3 ORDER BY id_tour DESC LIMIT 1";
    
    private static final String SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID = "SELECT path_image FROM tours WHERE id_tour=? AND id_type=3";
    
    private static final String SQL_INSERT_SHOPPING = "INSERT INTO tours(name,summary,description,departure_date,arrival_date,price," +
            "hot_tour,shops,id_transport,services,path_image,id_type) VALUES(?,?,?,?,?,?,?,?,(SELECT id_transport " +
            "FROM transport_type WHERE transport_type=?),?,?,3)";

    private static final String SQL_INSERT_SHOPPING_CITY = "INSERT INTO tours_cities(id_tour,id_city,`order`) VALUES(?,?,1)";

    private static final String SQL_UPDATE_SHOPPING_CITY = "UPDATE tours_cities SET id_city=? WHERE id_tour=?";
    
    private static final String SQL_UPDATE_SHOPPING = "UPDATE tours SET name=?,summary=?,description=?,departure_date=?,arrival_date=?," +
            "price=?,hot_tour=?,shops=?,id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?),services=?," +
            "path_image=? WHERE id_tour=? AND id_type=3";
    
    private static final String SQL_DELETE_SHOPPING = "DELETE FROM tours WHERE id_tour=? AND id_type=3";

    private static final String SQL_DELETE_SHOPPING_CITY = "DELETE FROM tours_cities WHERE id_tour=?";

    private static final String PARAM_ID_TOUR = "id_tour";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SUMMARY = "summary";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_DEPARTURE_DATE = "departure_date";
    private static final String PARAM_ARRIVAL_DATE = "arrival_date";
    private static final String PARAM_PRICE = "price";
    private static final String PARAM_HOT_TOUR = "hot_tour";
    private static final String PARAM_ID_CITY = "id_city";
    private static final String PARAM_DESTINATION_CITY = "destination_city";
    private static final String PARAM_ID_COUNTRY = "id_country";
    private static final String PARAM_DESTINATION_COUNTRY = "destination_country";
    private static final String PARAM_TRANSPORT = "transport";
    private static final String PARAM_SERVICES = "services";
    private static final String PARAM_PATH_IMAGE = "path_image";
    private static final String PARAM_SHOPS = "shops";

    private static final HashMap<String, String> mapForSortCriterion = new HashMap<>();

    static {
        mapForSortCriterion.put("name", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_NAME);
        mapForSortCriterion.put("departure_date", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
        mapForSortCriterion.put("arrival_date", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
        mapForSortCriterion.put("price", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_PRICE);
        mapForSortCriterion.put("transport", SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW_SORT_BY_TRANSPORT);
    }


    private JdbcShoppingDAO() {
    }


    private static class JdbcShoppingDAOHolder {
        private static final JdbcShoppingDAO HOLDER_INSTANCE = new JdbcShoppingDAO();
    }


    public static JdbcShoppingDAO getInstance() {
        return JdbcShoppingDAOHolder.HOLDER_INSTANCE;
    }


    public List<Shopping> findAllShoppingsAfterNow(Date nowDate) throws DAOException {
        return selectShoppingsAfterNowWithQuery(nowDate, SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW);
    }


    public List<Shopping> findAllSortShoppingsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(mapForSortCriterion.getOrDefault(criterion, SQL_SELECT_ALL_SHOPPINGS_AFTER_NOW))) {
            ps.setDate(1,nowDate);
            ResultSet resultSet = ps.executeQuery();
            shoppings = getListShoppings(resultSet);
            if (!order){
                Collections.reverse(shoppings);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return shoppings;
    }


    public List<Shopping> findShoppingsByNameAfterNow(Date nowDate, String name) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_NAME_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,"%" + name + "%");
            ResultSet resultSet = ps.executeQuery();
            return getListShoppings(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }

    public List<Shopping> findShoppingsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException {
        return findShoppingByDateWithQuery(nowDate, departureDate, SQL_SELECT_SHOPPINGS_BY_DEPARTURE_DATE_AFTER_NOW);
    }

    public List<Shopping> findShoppingsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException {
        return findShoppingByDateWithQuery(nowDate, arrivalDate, SQL_SELECT_SHOPPINGS_BY_ARRIVAL_DATE_AFTER_NOW);
    }


    public List<Shopping> findShoppingsByPriceAfterNow(Date nowDate, double price) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_PRICE_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setDouble(2,price);
            ResultSet resultSet = ps.executeQuery();
            return getListShoppings(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }


    public List<Shopping> findShoppingsByTransportAfterNow(Date nowDate, String transport) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPINGS_BY_TRANSPORT_AFTER_NOW)) {
            ps.setDate(1,nowDate);
            ps.setString(2,transport);
            ResultSet resultSet = ps.executeQuery();
            return getListShoppings(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }


    public Long findLastShoppingId() throws DAOException {
        Long id = 0L;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_SHOPPING_ID);
            if (resultSet.next()) {
                id = resultSet.getLong(PARAM_ID_TOUR);
                LOG.debug("Last shopping id: " + resultSet.getLong(PARAM_ID_TOUR));
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return id;
    }

    public String findPathImageShoppingById(Long id) throws DAOException {
        String pathImage = null;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_SHOPPING_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                pathImage = resultSet.getString(PARAM_PATH_IMAGE);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return pathImage;
    }


    @Override
    public boolean create(Shopping shopping) throws DAOException {
        boolean flag = true;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_SHOPPING);
             PreparedStatement ps2 = cn.prepareStatement(SQL_INSERT_SHOPPING_CITY);
             Statement st = cn.createStatement()) {
            fillShoppingPreparedStatement(shopping, ps);
            if (ps.executeUpdate() == 0){
                flag = false;
            }
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_SHOPPING_ID);
            Long shoppingId = null;
            while (resultSet.next()){
                shoppingId = resultSet.getLong(PARAM_ID_TOUR);
            }
            ps2.setLong(1,shoppingId);
            ps2.setLong(2,shopping.getCities().get(0).getId());
            if (ps2.executeUpdate() == 0){
                flag = false;
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }


    @Override
    public boolean update(Shopping shopping) throws DAOException {
        boolean flag = true;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_SHOPPING);
             PreparedStatement ps2 = cn.prepareStatement(SQL_UPDATE_SHOPPING_CITY)) {
            fillShoppingPreparedStatement(shopping, ps);
            ps.setLong(12,shopping.getId());
            if (ps.executeUpdate() == 0){
                flag = false;
            }
            ps2.setLong(1,shopping.getCities().get(0).getId());
            ps2.setLong(2,shopping.getId());
            if (ps2.executeUpdate() == 0){
                flag = false;
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }


    public List<Shopping> selectLastShoppings(Date nowDate) throws DAOException {
        return selectShoppingsAfterNowWithQuery(nowDate, SQL_SELECT_LAST_SHOPPINGS);
    }


    @Override
    public Shopping findEntityById(Long id) throws DAOException {
        Shopping shopping = new Shopping();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_SHOPPING_BY_ID)) {
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                shopping.setId(resultSet.getLong(PARAM_ID_TOUR));
                shopping.setName(resultSet.getString(PARAM_NAME));
                shopping.setSummary(resultSet.getString(PARAM_SUMMARY));
                shopping.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                shopping.setDepartureDate(resultSet.getDate(PARAM_DEPARTURE_DATE));
                shopping.setArrivalDate(resultSet.getDate(PARAM_ARRIVAL_DATE));
                shopping.setPrice(resultSet.getDouble(PARAM_PRICE));
                //shopping.setLastMinute(resultSet.getBoolean(PARAM_HOT_TOUR));
                shopping.setShops(resultSet.getString(PARAM_SHOPS));
                ArrayList<City> cities = new ArrayList<City>();
                City city = new City();
                city.setId(resultSet.getLong(PARAM_ID_CITY));
                city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                Country country = new Country();
                country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                city.setCountry(country);
                cities.add(city);
                shopping.setCities(cities);
                shopping.setTransport(Transport.valueOf(resultSet.getString(PARAM_TRANSPORT)));
                shopping.setServices(resultSet.getString(PARAM_SERVICES));
                shopping.setPathImage(resultSet.getString(PARAM_PATH_IMAGE));
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return shopping;
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = true;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_SHOPPING);
             PreparedStatement ps2 = cn.prepareStatement(SQL_DELETE_SHOPPING_CITY)) {
            ps2.setLong(1,id);
            if (ps2.executeUpdate() == 0){
                flag = false;
            }
            ps.setLong(1,id);
            if (ps.executeUpdate() == 0){
                flag = false;
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return flag;
    }


    private List<Shopping> getListShoppings(ResultSet resultSet) throws DAOException {
        List<Shopping> shoppings = new ArrayList<>();
        try {
            Map<Long, Shopping> shoppingMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                if (!shoppingMap.containsKey(resultSet.getLong(PARAM_ID_TOUR))) {
                    Shopping shopping = new Shopping();
                    shopping.setId(resultSet.getLong(PARAM_ID_TOUR));
                    shopping.setName(resultSet.getString(PARAM_NAME));
                    shopping.setSummary(resultSet.getString(PARAM_SUMMARY));
                    shopping.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                    shopping.setDepartureDate(resultSet.getDate(PARAM_DEPARTURE_DATE));
                    shopping.setArrivalDate(resultSet.getDate(PARAM_ARRIVAL_DATE));
                    shopping.setPrice(resultSet.getDouble(PARAM_PRICE));
                    //shopping.setLastMinute(resultSet.getBoolean(PARAM_HOT_TOUR));
                    shopping.setShops(resultSet.getString(PARAM_SHOPS));
                    ArrayList<City> cities = new ArrayList<City>();
                    City city = new City();
                    city.setId(resultSet.getLong(PARAM_ID_CITY));
                    city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                    Country country = new Country();
                    country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                    country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                    city.setCountry(country);
                    cities.add(city);
                    shopping.setCities(cities);
                    shopping.setTransport(Transport.valueOf(resultSet.getString(PARAM_TRANSPORT)));
                    shopping.setServices(resultSet.getString(PARAM_SERVICES));
                    shopping.setPathImage(resultSet.getString(PARAM_PATH_IMAGE));
                    shoppings.add(shopping);
                }
            }
            shoppings.addAll(shoppingMap.values());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
        return shoppings;
    }

    private List<Shopping> selectShoppingsAfterNowWithQuery(Date nowDate, String query) throws DAOException{
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ResultSet resultSet = ps.executeQuery();
            return getListShoppings(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }


    private List<Shopping> findShoppingByDateWithQuery(Date nowDate, Date generalDate, String query) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1,nowDate);
            ps.setDate(2,generalDate);
            ResultSet resultSet = ps.executeQuery();
            return getListShoppings(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }


    private void fillShoppingPreparedStatement(Shopping shopping, PreparedStatement ps) throws DAOException {
        try {
            ps.setString(1,shopping.getName());
            ps.setString(2,shopping.getSummary());
            ps.setString(3,shopping.getDescription());
            ps.setDate(4,new Date(shopping.getDepartureDate().getTime()));
            ps.setDate(5,new Date(shopping.getArrivalDate().getTime()));
            ps.setDouble(6,shopping.getPrice());
            ps.setString(9,shopping.getShops());
            ps.setString(10,shopping.getTransport().toString());
            ps.setString(11,shopping.getServices());
            ps.setString(12,shopping.getPathImage());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }
}
