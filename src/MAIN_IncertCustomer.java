import db.Schema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MAIN_IncertCustomer {
    public static void main(String[] args) {
        try {
            Connection connection= getConnection();
            PreparedStatement ps= connection.prepareStatement(Schema.INSERT_COUSTOMER);
            ps.setString(1,"Dima");
            ps.setString(2,"Averbukh");
            ps.setString(3,"averbukh.dima@gmail.com");
            ps.setString(4,"qwerty");
            ps.executeUpdate();
            System.out.println("Customer has been added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cs?serverTimezone=UTC";
        String user = "root";
        String password = "1111";

        return DriverManager.getConnection(url, user, password);
    }
}