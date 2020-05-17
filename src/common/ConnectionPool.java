package common;

import common.ex.SystemMalfunctionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

    private static final int MAX_CONNECTIONS = 10;
    private static ConnectionPool instance;
    private final BlockingQueue<Connection> connections;

    private ConnectionPool() throws SystemMalfunctionException {
        connections = new LinkedBlockingQueue<>(MAX_CONNECTIONS);
        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                connections.offer(createConnection());
            }
        } catch (SQLException e) {
            throw new SystemMalfunctionException("FATAL: ConnectionPool creation failed!" + e.getMessage());
        }
    }

    public static ConnectionPool getInstance() throws SystemMalfunctionException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Coupon_system?serverTimezone=UTC";
        String userName = "root";
        String password = "1111";

        return DriverManager.getConnection(url, userName, password);
    }

    public Connection getConnection() throws SystemMalfunctionException {
        try {
            return connections.take();
        } catch (InterruptedException e) {
            throw new SystemMalfunctionException("Unable to get a connection! " + e.getMessage());
        }
    }

    public void returnConnection(Connection connection) throws SystemMalfunctionException {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            throw new SystemMalfunctionException("Unable to return a connection! " + e.getMessage());
        }
    }

    public void closeAllConnections() throws SystemMalfunctionException {
        Connection connection;
        while ((connection = connections.poll()) != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new SystemMalfunctionException("FATAL: Unable to close all connections!" + e.getMessage());
            }
        }
    }
}