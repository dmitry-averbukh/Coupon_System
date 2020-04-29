import common.ex.NoSuchCustumerException;
import common.ex.SystemMalfunctionException;
import data.db.CompanyDBDao;
import data.db.CouponDBDao;
import data.db.CustomerDBDao;
import data.ex.NoSuchCompanyException;
import data.ex.NoSuchCouponException;
import data.ex.ZeroCouponAmountException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCheck {
    public static void main(String[] args) throws SystemMalfunctionException, NoSuchCompanyException, NoSuchCustumerException, SQLException, ZeroCouponAmountException, NoSuchCouponException {
        try {
            Connection connection = getConnection();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.err.println("There was a problem getting a connection! " + e.getMessage());
        }

        CustomerDBDao customerDBDao = new CustomerDBDao();
        CompanyDBDao companyDBDao = new CompanyDBDao();
        CouponDBDao couponDBDao = new CouponDBDao();
        System.out.println(customerDBDao.getCoupons(4));
    }


    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Coupon_system?serverTimezone=UTC";
        String userName = "root";
        String password = "1111";

        return DriverManager.getConnection(url, userName, password);
    }
}

