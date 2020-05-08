package facade;

import model.Company;
import model.Coupon;

import java.sql.Date;
import java.util.Collection;

public class CompanyFacade {

    private Company company;
    // TODO: 05-May-20 Declare relevant DAOs

    public static AbsFacade performLogin(String userName, String password) {
        return null;
    }

    public void createCoupon(Coupon coupon) {
        // TODO: 05-May-20 Implement.
        // Creation of a coupon with the same title, is not allowed.
    }

    public void removeCoupon(long couponId) {
        // TODO: 05-May-20 Implement.
        // We can only coupons that belong to this company.
    }

    public void updateCoupon(Coupon coupon) {
        // TODO: 05-May-20 Implement.
        // It is not possible to update coupon's title.
    }

    public Coupon getCoupon(long couponId) {
        // TODO: 05-May-20 Implement.
        return null;
    }

    public Collection<Coupon> getAllCoupons(){
        // TODO: 05-May-20 Implement.
        return null;
    }

    public Collection<Coupon> getCouponsByCategory(int category) {
        // TODO: 05-May-20 Implement.
        return null;
    }

    public Collection<Coupon> getCouponsLowerThanPrice(double price) {
        // TODO: 05-May-20 Implement.
        return null;
    }

    public Collection<Coupon> getCouponsBeforeEndDate(Date endDate) {
        // TODO: 05-May-20 Implement.
        // Get all the coupons that their endDate is lower than the given endDate.
        return null;
    }
}
