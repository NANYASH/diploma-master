package dao;




import dao.exception.DAOException;
import entity.Entity;


public interface EntityDAO<K, T extends Entity> {

    T findEntityById(K id) throws DAOException;

    boolean create(T entity) throws DAOException;

    boolean update(T entity) throws DAOException;

    boolean delete(K id) throws DAOException;
}
