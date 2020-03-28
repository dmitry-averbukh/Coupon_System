import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    String url = "jdbc:mysql://localhost:3306/cs";
    String user = "root";
    String password = "1111";
    Connection connection1 = DriverManager.getConnection(url, user, password);
    Connection connection2 = DriverManager.getConnection(url, user, password);
    Connection connection3 = DriverManager.getConnection(url, user, password);
    LinkedList<Connection> connections = new LinkedList();

    public ConnectionPool() throws SQLException {
        connections.add(connection1);
        connections.add(connection2);
        connections.add(connection3);
    }


    public synchronized Connection get() {
        while (connections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Connection connection = connections.get(0);
        connections.remove(connection);
        return connection;
    }

    public synchronized void put(Connection connection) {
        connections.add(connection);
        notify();
    }
}