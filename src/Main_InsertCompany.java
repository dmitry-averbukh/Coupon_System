import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main_InsertCompany {
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            
            PreparedStatement preparedStatement = connection.prepareStatement("insert into company (name,email,password) values (?,?,?) ");

            preparedStatement.setString(1,"heh");
            preparedStatement.setString(2,"heh@gmail.com");
            preparedStatement.setString(3,"password");

            preparedStatement.executeUpdate();

            System.out.println("Company has been added successfully");
        } catch (SQLException e) {
            System.err.println("problem with connection to DB!");
        }

    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cs";
        String user = "root";
        String password = "1111";

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
