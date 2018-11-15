package dao.jdbc;

import dao.CityDAO;
import dao.exception.DAOException;
import entity.City;
import entity.Country;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCityDAO implements CityDAO {

    private static final Logger LOG = Logger.getLogger(JdbcCityDAO.class);

    private static final String SQL_SELECT_ALL_CITIES = "SELECT cities.id_city,cities.name,cities.id_country,countries.name FROM cities JOIN countries USING (id_country) ORDER BY cities.name;";//+

    private static final String SQL_SELECT_CITY_BY_ID = "SELECT id_city,cities.name AS name_city,id_country,countries.name " +
            "AS name_country FROM cities JOIN countries USING (id_country) WHERE id_city=?";//+

    private static final String SQL_INSERT_CITY = "INSERT INTO cities(name,id_country) VALUES(?,?)";//+

    private static final String SQL_UPDATE_CITY = "UPDATE cities SET name=?,id_country=? WHERE id_city=?";

    private static final String SQL_DELETE_CITY = "DELETE FROM cities WHERE id_city=?";

    private static final String PARAM_ID_CITY = "id_city";

    private static final String PARAM_NAME_CITY = "name";

    private static final String PARAM_ID_COUNTRY = "id_country";

    private static final String PARAM_NAME_COUNTRY = "countries.name";


    JdbcCityDAO() {
    }


    private static class JdbcCityDAOHolder {
        private static final JdbcCityDAO HOLDER_INSTANCE = new JdbcCityDAO();
    }

    public static JdbcCityDAO getInstance() {
        return JdbcCityDAOHolder.HOLDER_INSTANCE;
    }



   @Override
   public List<City> findAllCities() throws DAOException {
       List<City> cities = new ArrayList<>();
       try(Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
           Statement st = cn.createStatement()) {
           ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_CITIES);
           createCites(resultSet,cities);
       } catch (SQLException e) {
           throw new DAOException("SQL exception (request or table failed): " + e, e);
       }
       return cities;
   }



    @Override
    public boolean create(City city) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT_CITY)) {
            ps.setString(1, city.getName());
            ps.setLong(2, city.getCountry().getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }

    @Override
    public City findEntityById(Long id) throws DAOException {
        City city = new City();
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_CITY_BY_ID)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                city.setId(resultSet.getLong(PARAM_ID_CITY));
                city.setName(resultSet.getString(PARAM_NAME_CITY));
                Country country = new Country();
                country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
                country.setNameCountry(resultSet.getString(PARAM_NAME_COUNTRY));
                city.setCountry(country);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
        return city;
    }


    @Override
    public boolean delete(Long id) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE_CITY)) {
            ps.setLong(1, id);
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }

    @Override
    public boolean update(City city) throws DAOException {
        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency","root","asol1998");
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_CITY)) {
            ps.setString(1, city.getName());
            ps.setLong(2, city.getCountry().getId());
            ps.setLong(3, city.getId());
            return (ps.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (request or table failed): " + e, e);
        }
    }

    private void createCites(ResultSet resultSet, List<City> cities) throws SQLException {
        while (resultSet.next()) {
            City city = new City();
            city.setId(resultSet.getLong(PARAM_ID_CITY));
            city.setName(resultSet.getString(PARAM_NAME_CITY));
            Country country = new Country();
            country.setId(resultSet.getLong(PARAM_ID_COUNTRY));
            country.setNameCountry(resultSet.getString(PARAM_NAME_COUNTRY));
            city.setCountry(country);
            cities.add(city);
        }
    }
}
