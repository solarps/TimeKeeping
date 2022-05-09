package com.my.timekeeping;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class for implementation connection pool. (Singleton pattern)
 *
 * @author Andrey
 * @version 1.0
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static DataSource ds;

    /**
     This block for takes datasource from JNDI context.
     */
    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/datasource/ds");
            logger.info("Connection Pool initialized");
        } catch (NamingException e) {
            logger.fatal("Cannot init connection pool: {}", e.getMessage());
        }
    }


    private ConnectionPool() {
    }

    /**
     * This method for getting instance of connection pool class.
     * If instance doesn't exist, method create new instance.
     *
     * @return instance of connection pool class.
     */
    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * This method for getting connection from datasource.
     *
     * @return connection.
     */
    public Connection getConnection() throws SQLException {
        Connection connection = ds.getConnection();
        logger.trace("Get connection {}", connection);
        return connection;
    }
}
