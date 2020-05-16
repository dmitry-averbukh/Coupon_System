package data.db;

import common.ConnectionPool;
import common.StatementUtils;
import common.ex.SystemMalfunctionException;
import data.dao.CouponDao;
import data.ex.NoSuchCouponException;
import data.ex.ZeroCouponAmountException;
import model.Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CouponDBDao implements CouponDao {

    private static void applyCouponValuesOnStatement(Coupon coupon, PreparedStatement ps) throws SQLException {
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

    public static Coupon setCouponValuesOnStatement(ResultSet resultSet) throws SQLException {
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getInt(1));
        coupon.setCompanyId(resultSet.getInt(2));
        coupon.setCategory(resultSet.getInt(3));
        coupon.setTitle(resultSet.getString(4));
        coupon.setStartDate(resultSet.getDate(5));
        coupon.setEndDate(resultSet.getDate(6));
        coupon.setAmount(resultSet.getInt(7));
        coupon.setDescription(resultSet.getString(8));
        coupon.setPrice(resultSet.getDouble(9));
        coupon.setImage(resultSet.getString(10));
        return coupon;
    }

    @Override
    public void createCoupon(Coupon coupon) throws SystemMalfunctionException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Schema.INSERT_COUPON);
            applyCouponValuesOnStatement(coupon, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SystemMalfunctionException("There was a problem creating coupon: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(preparedStatement);
        }
    }

    @Override
    public void removeCoupon(long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionPool.getInstance()
                    .getConnection()
                    .prepareStatement(Schema.REMOVE_COUPON);
            preparedStatement.setLong(1, id);
            int rowsAffective = preparedStatement.executeUpdate();
            if (rowsAffective == 0) {
                String msg = "There is no coupon with id %d";
                throw new NoSuchCouponException(String.format(msg, id));
            }
        } catch (SQLException | SystemMalfunctionException | NoSuchCouponException e) {
            e.printStackTrace();
        } finally {
            StatementUtils.close(preparedStatement);
        }
    }

    @Override
    public void removeCompanyCoupons(long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionPool.getInstance()
                    .getConnection()
                    .prepareStatement(Schema.REMOVE_COMPANY_COUPONS);
            preparedStatement.setLong(1, id);
            int rowsAffective = preparedStatement.executeUpdate();
            if (rowsAffective == 0) {
                String msg = "There is no coupon with id %d";
                throw new NoSuchCouponException(String.format(msg, id));
            }
        } catch (SQLException | SystemMalfunctionException | NoSuchCouponException e) {
            e.printStackTrace();
        } finally {
            StatementUtils.close(preparedStatement);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) throws SystemMalfunctionException, SQLException, NoSuchCouponException {
        PreparedStatement preparedStatement;
        preparedStatement = ConnectionPool.getInstance()
                .getConnection()
                .prepareStatement(Schema.UPDATE_COUPON);
        applyCouponValuesOnStatement(coupon, preparedStatement);
        int executeUpdate = preparedStatement.executeUpdate();
        if (executeUpdate == 0)
            throw new NoSuchCouponException("You don't have a coupon with that id ");
        StatementUtils.close(preparedStatement);
    }

    @Override
    public void decrementCouponAmount(long id) throws SystemMalfunctionException, SQLException, ZeroCouponAmountException {
        PreparedStatement preparedStatement;
        preparedStatement = ConnectionPool.getInstance()
                .getConnection()
                .prepareStatement(Schema.DECREMENT_COUPON);
        preparedStatement.setLong(1, id);
        int rowsAffective = preparedStatement.executeUpdate();
        if (rowsAffective == 0) {
            String msg = "There is no more coupons with id %d";
            throw new ZeroCouponAmountException(String.format(msg, id));
        }
        StatementUtils.close(preparedStatement);
    }

    @Override
    public Coupon getCoupon(long id) throws SystemMalfunctionException, SQLException, NoSuchCouponException {
        if (id <= 0)
            throw new NoSuchCouponException("you don't have coupon with that id!");
        final Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSetOfSelectedCoupon = connection
                .createStatement()
                .executeQuery(Schema.GET_COUPON + id);

        if (resultSetOfSelectedCoupon.first()) {
            return setCouponValuesOnStatement(resultSetOfSelectedCoupon);
        }
        throw new NoSuchCouponException("you don't have coupon with that id!");
    }

    @Override
    public Collection<Coupon> getAllCoupons() throws SystemMalfunctionException, SQLException, NoSuchCouponException {
        ArrayList<Coupon> allCoupons = new ArrayList<>();
        final Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSetOfAllCoupons = connection
                .createStatement()
                .executeQuery(Schema.GET_ALL_COUPONS);
        while (resultSetOfAllCoupons.next()) {
            allCoupons.add(getCoupon(resultSetOfAllCoupons.getLong(1)));
        }
        return allCoupons;
    }

    @Override
    public Collection<Coupon> getAllCoupons(int category) throws SystemMalfunctionException, SQLException, NoSuchCouponException {
        ArrayList<Coupon> allCoupons = new ArrayList<>();
        final Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSetOfSelectedCoupons = connection
                .createStatement()
                .executeQuery(Schema.GET_ALL_COUPONS_CATEG + category);
        while (resultSetOfSelectedCoupons.next()) {
            allCoupons.add(getCoupon(resultSetOfSelectedCoupons.getLong(1)));
        }
        if (allCoupons.size() != 0)
            return allCoupons;
        else
            throw new NoSuchCouponException("you don't have coupon with that category!");
    }
}
