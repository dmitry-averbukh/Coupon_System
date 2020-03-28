import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {
    public static void main(String[] args) throws SQLException, InterruptedException {
        ConnectionPool connectionPool= new ConnectionPool();
        for (int i = 0; i < 10; i++) {
            Connection connection=connectionPool.get();
            System.out.println("connection # "+connection);
            Thread.sleep(1000);
            connectionPool.put(connection);
        }
    }
}
