package dao.jdbc;

import dao.TripDAO;
import dao.exception.DAOException;
import entity.City;
import entity.Country;
import entity.Transport;
import entity.Trip;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class JdbcTripDAO implements TripDAO {

    private final static Logger LOG = Logger.getLogger(JdbcTripDAO.class);

    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 " +
            "ORDER BY tours_cities.`order`";

    private static final String SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND tours.name LIKE ? AND id_type=2 " +
            "ORDER BY tours_cities.`order`";

    private static final String SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND departure_date=? AND id_type=2 " +
            "ORDER BY tours_cities.`order`";

    private static final String SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND arrival_date=? AND id_type=2 " +
            "ORDER BY tours_cities.`order`";

    private static final String SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW = "SELECT id_tour,tours.name,summary,description,departure_date," +
            "arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND price=? AND id_type=2 " +
            "ORDER BY tours_cities.`order`";

    private static final String SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>?" +
            " AND id_transport=(SELECT id_transport FROM transport_type WHERE transport_type=?) AND id_type=2 " +
            "ORDER BY tours_cities.`order`";

    private static final String SQL_SELECT_TRIP_BY_ID = "SELECT id_tour,tours.name,summary,description,departure_date,arrival_date," +
            "price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city,cities.id_country AS id_country, " +
            "countries.name AS destination_country,transport_type.transport_type as transport,services,path_image FROM tours " +
            "JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) " +
            "JOIN countries USING (id_country) WHERE id_tour=? AND id_type=2 ORDER BY tours_cities.`order`";

    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 " +
            "ORDER BY tours.name,tours_cities.`order`";

    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE = "SELECT id_tour,tours.name,summary," +
            "description,departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, " +
            "cities.name AS destination_city,cities.id_country AS id_country, countries.name AS destination_country," +
            "transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) " +
            "RIGHT JOIN tours_cities USING (id_tour) JOIN cities USING (id_city) JOIN countries USING (id_country) " +
            "WHERE departure_date>? AND id_type=2 ORDER BY departure_date,tours_cities.`order`";

    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 " +
            "ORDER BY arrival_date,tours_cities.`order`";

    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 " +
            "ORDER BY price,tours_cities.`order`";

    private static final String SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT = "SELECT id_tour,tours.name,summary,description," +
            "departure_date,arrival_date,price,hot_tour,attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,transport_type.transport_type as transport," +
            "services,path_image FROM tours JOIN transport_type USING (id_transport) RIGHT JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country) WHERE departure_date>? AND id_type=2 " +
            "ORDER BY transport,tours_cities.`order`";

    private static final String SQL_SELECT_LAST_TRIPS = "SELECT t.id_tour,t.name,t.summary,t.description,t.departure_date," +
            "t.arrival_date,t.price,t.hot_tour,t.attractions,cities.id_city AS id_city, cities.name AS destination_city," +
            "cities.id_country AS id_country, countries.name AS destination_country,t.transport,t.services,t.path_image " +
            "FROM (SELECT id_tour,tours.name,summary,description,departure_date,arrival_date,price,hot_tour,attractions," +
            "transport_type.transport_type as transport,services,path_image FROM tours JOIN transport_type USING (id_transport) " +
            "WHERE departure_date>? AND id_type=2 ORDER BY id_tour DESC LIMIT 6) t JOIN tours_cities USING (id_tour) " +
            "JOIN cities USING (id_city) JOIN countries USING (id_country)";

    private static final String SQL_SELECT_LAST_TRIP_ID = "SELECT id_tour FROM tours WHERE id_type=2 ORDER BY id_tour DESC LIMIT 1";

    private static final String SQL_SELECT_PATH_IMAGE_TRIP_BY_ID = "SELECT path_image FROM tours WHERE id_tour=? AND id_type=2";

    private static final String SQL_INSERT_TRIP = "INSERT INTO tours(name,summary,description,departure_date,arrival_date,price," +
            "hot_tour,attractions,id_transport,services,path_image,id_type) VALUES(?,?,?,?,?,?,?,?," +
            "(SELECT id_transport FROM transport_type WHERE transport_type=?),?,?,2)";

    private static final String SQL_INSERT_TRIP_CITY = "INSERT INTO tours_cities(id_tour,id_city,`order`) VALUES(?,?,?)";

    private static final String SQL_UPDATE_TRIP = "UPDATE tours SET name=?,summary=?,description=?,departure_date=?,arrival_date=?," +
            "price=?,hot_tour=?,attractions=?,id_transport=(SELECT id_transport FROM transport_type " +
            "WHERE transport_type=?),services=?,path_image=? WHERE id_tour=? AND id_type=2";

    private static final String SQL_DELETE_TRIP = "DELETE FROM tours WHERE id_tour=? AND id_type=2";

    private static final String SQL_DELETE_TRIP_CITY = "DELETE FROM tours_cities WHERE id_tour=?";

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
    private static final String PARAM_ATTRACTIONS = "attractions";

    private static final HashMap<String, String> mapForSortCriterion = new HashMap<>();

    static {
        mapForSortCriterion.put("name", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_NAME);
        mapForSortCriterion.put("departure_date", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_DEPARTURE_DATE);
        mapForSortCriterion.put("arrival_date", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_ARRIVAL_DATE);
        mapForSortCriterion.put("price", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_PRICE);
        mapForSortCriterion.put("transport", SQL_SELECT_ALL_TRIPS_AFTER_NOW_SORT_BY_TRANSPORT);
    }


    private JdbcTripDAO() {
    }


    private static class JdbcTripDAOHolder {
        private static final JdbcTripDAO HOLDER_INSTANCE = new JdbcTripDAO();
    }



    public static JdbcTripDAO getInstance() {
        return JdbcTripDAOHolder.HOLDER_INSTANCE;
    }


    public List<Trip> findAllTripsAfterNow(Date nowDate) throws DAOException {
        return selectTripsAfterNowWithQuery(nowDate, SQL_SELECT_ALL_TRIPS_AFTER_NOW);
    }


    public List<Trip> findAllSortTripsAfterNow(Date nowDate, String criterion, boolean order) throws DAOException {
        List<Trip> trips = new ArrayList<>();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(mapForSortCriterion.getOrDefault(criterion, SQL_SELECT_ALL_TRIPS_AFTER_NOW))) {
            ps.setDate(1, nowDate);
            ResultSet resultSet = ps.executeQuery();
            trips.addAll(getListTrips(resultSet));
            if (!order) {
                Collections.reverse(trips);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return trips;
    }


    public List<Trip> findTripsByNameAfterNow(Date nowDate, String name) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_NAME_AFTER_NOW)) {
            ps.setDate(1, nowDate);
            ps.setString(2, "%" + name + "%");
            ResultSet resultSet = ps.executeQuery();
            return getListTrips(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }

    public List<Trip> findTripsByDepartureDateAfterNow(Date nowDate, Date departureDate) throws DAOException {
        return findTripByDateWithQuery(nowDate, departureDate, SQL_SELECT_TRIPS_BY_DEPARTURE_DATE_AFTER_NOW);
    }


    public List<Trip> findTripsByArrivalDateAfterNow(Date nowDate, Date arrivalDate) throws DAOException {
        return findTripByDateWithQuery(nowDate, arrivalDate, SQL_SELECT_TRIPS_BY_ARRIVAL_DATE_AFTER_NOW);
    }


    public List<Trip> findTripsByPriceAfterNow(Date nowDate, double price) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_PRICE_AFTER_NOW)) {
            ps.setDate(1, nowDate);
            ps.setDouble(2, price);
            ResultSet resultSet = ps.executeQuery();
            return getListTrips(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }


    public List<Trip> findTripsByTransportAfterNow(Date nowDate, String transport) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIPS_BY_TRANSPORT_AFTER_NOW)) {
            ps.setDate(1, nowDate);
            ps.setString(2, transport);
            ResultSet resultSet = ps.executeQuery();
            return getListTrips(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }


    public Long findLastTripId() throws DAOException {
        Long id = 0L;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             Statement st = cn.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_TRIP_ID);
            if (resultSet.next()) {
                id = resultSet.getLong(PARAM_ID_TOUR);
                LOG.debug("Last trip id: " + resultSet.getLong(PARAM_ID_TOUR));
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return id;
    }


    public String findPathImageTripById(Long id) throws DAOException {
        String pathImage = null;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_PATH_IMAGE_TRIP_BY_ID)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                pathImage = resultSet.getString(PARAM_PATH_IMAGE);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return pathImage;
    }


    @Override
    public boolean create(Trip trip) throws DAOException {
        boolean flag = false;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_TRIP);
             PreparedStatement ps2 = cn.prepareStatement(SQL_INSERT_TRIP_CITY);
             Statement st = cn.createStatement()) {
            fillTripPreparedStatement(trip, ps);
            if (ps.executeUpdate() != 0){
                flag = true;
            }
            ResultSet resultSet = st.executeQuery(SQL_SELECT_LAST_TRIP_ID);
            Long tripId = null;
            while (resultSet.next()) {
                tripId = resultSet.getLong(PARAM_ID_TOUR);
            }
            for (int i = 0; i < trip.getCities().size(); i++) {
                ps2.setLong(1, tripId);
                ps2.setLong(2, trip.getCities().get(i).getId());
                ps2.setInt(3, i + 1);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return flag;
    }


    @Override
    public boolean update(Trip trip) throws DAOException {
        boolean flag = true;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_TRIP);
             PreparedStatement ps2 = cn.prepareStatement(SQL_INSERT_TRIP_CITY);
             PreparedStatement ps3 = cn.prepareStatement(SQL_DELETE_TRIP_CITY)) {
            fillTripPreparedStatement(trip, ps);
            ps.setLong(12, trip.getId());
            if (ps.executeUpdate() == 0){
                flag = false;
            }
            LOG.debug("Trip edit ps1 flag: " + flag);
            ps3.setLong(1, trip.getId());
            if (ps3.executeUpdate() == 0){
                flag = false;
            }
            LOG.debug("Trip edit ps3 flag: " + flag);
            for (int i = 0; i < trip.getCities().size(); i++) {
                ps2.setLong(1, trip.getId());
                ps2.setLong(2, trip.getCities().get(i).getId());
                ps2.setInt(3, i + 1);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return flag;
    }


    public List<Trip> selectLastTrips(Date nowDate) throws DAOException {
        return selectTripsAfterNowWithQuery(nowDate, SQL_SELECT_LAST_TRIPS);
    }


    @Override
    public Trip findEntityById(Long id) throws DAOException {
        Trip trip = new Trip();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_TRIP_BY_ID)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (trip.getId() == 0L) {
                    trip.setId(resultSet.getLong(PARAM_ID_TOUR));
                    trip.setName(resultSet.getString(PARAM_NAME));
                    trip.setSummary(resultSet.getString(PARAM_SUMMARY));
                    trip.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                    trip.setDepartureDate(resultSet.getDate(PARAM_DEPARTURE_DATE));
                    trip.setArrivalDate(resultSet.getDate(PARAM_ARRIVAL_DATE));
                    trip.setPrice(resultSet.getDouble(PARAM_PRICE));
                    //trip.setLastMinute(resultSet.getBoolean(PARAM_HOT_TOUR));
                    trip.setAttractions(resultSet.getString(PARAM_ATTRACTIONS));
                    trip.setTransport(Transport.valueOf(resultSet.getString(PARAM_TRANSPORT)));
                    trip.setServices(resultSet.getString(PARAM_SERVICES));
                    trip.setPathImage(resultSet.getString(PARAM_PATH_IMAGE));
                    ArrayList<City> cities = new ArrayList<City>();
                    City city = new City();
                    city.setId(resultSet.getLong(PARAM_ID_CITY));
                    city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                    Country country = new Country();
                    country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                    country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                    city.setCountry(country);
                    cities.add(city);
                    trip.setCities(cities);
                } else {
                    City city = new City();
                    city.setId(resultSet.getLong(PARAM_ID_CITY));
                    city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                    Country country = new Country();
                    country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                    country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                    city.setCountry(country);
                    trip.getCities().add(city);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return trip;
    }


    @Override
    public boolean delete(Long id) throws DAOException {
        boolean flag = true;
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_TRIP);
             PreparedStatement ps2 = cn.prepareStatement(SQL_DELETE_TRIP_CITY)) {
            ps2.setLong(1, id);
            if (ps2.executeUpdate() == 0){
                flag = false;
            }
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0){
                flag = false;
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return flag;
    }


    private List<Trip> getListTrips(ResultSet resultSet) throws DAOException {
        List<Trip> trips = new ArrayList<>();
        try {
            Map<Long, Trip> tripMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                if (!tripMap.containsKey(resultSet.getLong(PARAM_ID_TOUR))) {
                    Trip trip = new Trip();
                    trip.setId(resultSet.getLong(PARAM_ID_TOUR));
                    trip.setName(resultSet.getString(PARAM_NAME));
                    trip.setSummary(resultSet.getString(PARAM_SUMMARY));
                    trip.setDescription(resultSet.getString(PARAM_DESCRIPTION));
                    trip.setDepartureDate(resultSet.getDate(PARAM_DEPARTURE_DATE));
                    trip.setArrivalDate(resultSet.getDate(PARAM_ARRIVAL_DATE));
                    trip.setPrice(resultSet.getDouble(PARAM_PRICE));
                    //trip.setLastMinute(resultSet.getBoolean(PARAM_HOT_TOUR));
                    trip.setAttractions(resultSet.getString(PARAM_ATTRACTIONS));
                    trip.setTransport(Transport.valueOf(resultSet.getString(PARAM_TRANSPORT)));
                    trip.setServices(resultSet.getString(PARAM_SERVICES));
                    trip.setPathImage(resultSet.getString(PARAM_PATH_IMAGE));
                    ArrayList<City> cities = new ArrayList<City>();
                    City city = new City();
                    city.setId(resultSet.getLong(PARAM_ID_CITY));
                    city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                    Country country = new Country();
                    country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                    country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                    city.setCountry(country);
                    cities.add(city);
                    trip.setCities(cities);
                    tripMap.put(trip.getId(), trip);
                } else {
                    City city = new City();
                    city.setId(resultSet.getLong(PARAM_ID_CITY));
                    city.setName(resultSet.getString(PARAM_DESTINATION_CITY));
                    Country country = new Country();
                    country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                    country.setNameCountry(resultSet.getString(PARAM_DESTINATION_COUNTRY));
                    city.setCountry(country);
                    tripMap.get(resultSet.getLong(PARAM_ID_TOUR)).getCities().add(city);
                }
            }
            trips.addAll(tripMap.values());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return trips;
    }


    private List<Trip> selectTripsAfterNowWithQuery(Date nowDate, String query) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1, nowDate);
            ResultSet resultSet = ps.executeQuery();
            return getListTrips(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }


    private List<Trip> findTripByDateWithQuery(Date nowDate, Date generalDate, String query) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setDate(1, nowDate);
            ps.setDate(2, generalDate);
            ResultSet resultSet = ps.executeQuery();
            return getListTrips(resultSet);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }


    private void fillTripPreparedStatement(Trip trip, PreparedStatement ps) throws DAOException {
        try {
            ps.setString(1, trip.getName());
            ps.setString(2, trip.getSummary());
            ps.setString(3, trip.getDescription());
            ps.setDate(4, new Date(trip.getDepartureDate().getTime()));
            ps.setDate(5, new Date(trip.getArrivalDate().getTime()));
            ps.setDouble(6, trip.getPrice());
            ps.setString(7, trip.getAttractions());
            ps.setString(8, trip.getTransport().toString());
            ps.setString(9, trip.getServices());
            ps.setString(10, trip.getPathImage());
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e,e);
        }
    }
}
