import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCheck {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cs";
        String user = "root";
        String password = "1111";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("connected");
        } catch (SQLException e) {
            System.err.println("problem connecting to DB!");
        }
    }
}
