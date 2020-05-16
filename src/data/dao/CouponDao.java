package data.dao;

import common.ex.SystemMalfunctionException;
import data.ex.NoSuchCouponException;
import data.ex.ZeroCouponAmountException;
import model.Company;
import model.Coupon;

import java.sql.SQLException;
import java.util.Collection;

/**
 * This interface is used to define a Data Access Object for the coupon data-source.
 */

public interface CouponDao {
    void createCoupon(Coupon coupon) throws SystemMalfunctionException;

    void removeCoupon(long id);

    void removeCompanyCoupons(long id);

    void updateCoupon(Coupon coupon) throws SystemMalfunctionException, SQLException, NoSuchCouponException;

    void decrementCouponAmount(long id) throws SystemMalfunctionException, SQLException, ZeroCouponAmountException;

    Coupon getCoupon(long id) throws SystemMalfunctionException, SQLException, NoSuchCouponException;

    Collection<Coupon> getAllCoupons() throws SystemMalfunctionException, SQLException, NoSuchCouponException;

    Collection<Coupon> getAllCoupons(int category) throws SystemMalfunctionException, SQLException, NoSuchCouponException;
}
