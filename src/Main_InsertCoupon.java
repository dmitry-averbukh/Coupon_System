import db.Schema;
import model.Coupon;

import java.sql.*;

public class Main_InsertCoupon {
    public static void main(String[] args) {

        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(Schema.INSERT_COUPON);
            applyCouponValuesOnStatement(ps, getSomeCoupon());
            ps.executeUpdate();
            System.out.println("Coupon has been added successfully");
        } catch (SQLException e) {
            System.out.println("There was a problem inserting a new coupon: " + e.getMessage());
        }
    }

    private static Coupon getSomeCoupon() {
        // TODO: 3/24/2020 Apply coupon values on statement.
        Coupon c = new Coupon();
        c.setCompanyId(2);
        c.setCategory(7);
        c.setTitle("50% discount");
        c.setStartDate(Date.valueOf("2020-03-24"));
        c.setEndDate(Date.valueOf("2021-03-24"));
        c.setAmount(4);
        c.setDescription("50% discount on all yellow T shirts!");
        c.setPrice(123.456);
        c.setImage("http://www.zara.com/coupons/coupon.png");

        return c;
    }

    private static void applyCouponValuesOnStatement(PreparedStatement ps, Coupon coupon) throws SQLException {
        ps.setLong(1, coupon.getCompanyId());
        ps.setInt(2, coupon.getCategory());
        ps.setString(3, coupon.getTitle());
        ps.setDate(4, coupon.getStartDate());
        ps.setDate(5, coupon.getEndDate());
        ps.setInt(6, coupon.getAmount());
        ps.setString(7, coupon.getDescription());
        ps.setDouble(8, coupon.getPrice());
        ps.setString(9, coupon.getImage());
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cs?serverTimezone=UTC";
        String user = "root";
        String password = "1111";

        return DriverManager.getConnection(url, user, password);
    }
}
