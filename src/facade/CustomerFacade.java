package facade;

import model.Coupon;
import model.Customer;

import java.util.Collection;

public class CustomerFacade {

    private Customer customer;
    // TODO: 05-May-20 Declare relevant DAOs

    public static AbsFacade performLogin(String userName, String password) {
        return null;
    }

    public void purchaseCoupon(long couponId) {
        // TODO: 05-May-20 Implement.
        // A customer can not hold the same coupons more than once.
    }

    public Collection<Coupon> getMyPurchasedCoupons() {
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
}
