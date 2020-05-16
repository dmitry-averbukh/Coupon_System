package facade;

import common.ex.NoSuchCustomerException;
import common.ex.SystemMalfunctionException;
import data.dao.CouponDao;
import data.dao.CustomerDao;
import data.db.CouponDBDao;
import data.db.CustomerDBDao;
import data.ex.CouponAlreadyPurchaseException;
import data.ex.InvalidLoginException;
import data.ex.NoSuchCouponException;
import model.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CustomerFacade extends AbsFacade {
    private CustomerDao customerDao;
    private long customerId;

    public CustomerFacade() {
        customerDao = new CustomerDBDao();
        CouponDao couponDao = new CouponDBDao();
    }

    public static AbsFacade performLogin(String userName, String password) throws SystemMalfunctionException, InvalidLoginException {
        if (new CustomerFacade().customerDao.login(userName, password) != null) {
            new CustomerFacade().setCoustomerId(new CustomerFacade().customerDao.login(userName, password).getId());
            return new CompanyFacade();
        }
        throw new InvalidLoginException("Unable to login with provided credentials as customer.");
    }

    public long getCoustomerId() {
        return customerId;
    }

    public void setCoustomerId(long coustomerId) {
        this.customerId = coustomerId;
    }

    public void purchaseCoupon(long couponId) throws SystemMalfunctionException, SQLException, NoSuchCouponException, NoSuchCustomerException, CouponAlreadyPurchaseException {
        customerDao.insertCustomerCoupon(couponId, customerId);
    }

    public Collection<Coupon> getMyPurchasedCoupons() throws NoSuchCustomerException, SQLException, SystemMalfunctionException {
        return customerDao.getCoupons(customerId);
    }

    public Collection<Coupon> getCouponsByCategory(int category) throws SystemMalfunctionException, NoSuchCustomerException, SQLException {
        ArrayList<Coupon> couponsByCategory = null;
        for (Coupon myPurchasedCoupon : getMyPurchasedCoupons()) {
            if (myPurchasedCoupon.getCategory() == category)
                couponsByCategory.add(myPurchasedCoupon);
        }
        return couponsByCategory;
    }

    public Collection<Coupon> getCouponsLowerThanPrice(double price) throws SystemMalfunctionException, NoSuchCustomerException, SQLException {
        ArrayList<Coupon> couponsLowPrice = null;
        for (Coupon myPurchasedCoupon : getMyPurchasedCoupons()) {
            if (myPurchasedCoupon.getPrice() < price)
                couponsLowPrice.add(myPurchasedCoupon);
        }
        return couponsLowPrice;
    }
}
