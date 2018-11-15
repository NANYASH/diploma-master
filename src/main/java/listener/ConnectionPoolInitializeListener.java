package listener;

import pool.ConnectionPool;
import pool.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolInitializeListener implements ServletContextListener {

    private final static Logger LOG = Logger.getLogger(ConnectionPoolInitializeListener.class);


    public static ConnectionPool connectionPool;

    private static final int POOL_SIZE = 20;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.init(POOL_SIZE);
        } catch (ConnectionPoolException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            connectionPool.destroy();
        } catch (ConnectionPoolException e) {
            LOG.error(e.getMessage());
        }
    }
}
