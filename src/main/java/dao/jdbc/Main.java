package dao.jdbc;

import dao.exception.DAOException;

/**
 * Created by nanya on 21118.
 */
public class Main {
    public static void main(String[] args) {
        JdbcCityDAO jdbcCityDAO = new JdbcCityDAO();
        try {
            jdbcCityDAO.findAllCities();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
