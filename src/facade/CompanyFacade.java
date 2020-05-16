package facade;

import common.ex.SystemMalfunctionException;
import data.dao.CompanyDao;
import data.dao.CouponDao;
import data.db.CompanyDBDao;
import data.db.CouponDBDao;
import data.ex.InvalidLoginException;
import data.ex.NoSuchCouponException;
import model.Coupon;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CompanyFacade extends AbsFacade {
    private final CompanyDao companyDao;
    private final CouponDao couponDao;
    private long companyId;

    public CompanyFacade() {
        companyDao = new CompanyDBDao();
        couponDao = new CouponDBDao();
    }

    public static AbsFacade performLogin(String userName, String password) throws InvalidLoginException, SystemMalfunctionException {
        if (new CompanyFacade().companyDao.login(userName, password) != null) {
            new CompanyFacade().setCompanyId(new CompanyFacade().companyDao.login(userName, password).getId());
            return new CompanyFacade();
        }
        throw new InvalidLoginException("Unable to login with provided credentials as company.");
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public void createCoupon(Coupon coupon) throws Exception {
        for (Coupon companyDaoCoupon : companyDao.getCoupons(coupon.getCompanyId())) {
            if (companyDaoCoupon.getDescription().equals(coupon.getDescription()))
                throw new Exception("You already have a coupon with that description!");
        }
        couponDao.createCoupon(coupon);
    }

    public void removeCoupon(long couponId) {
        couponDao.removeCoupon(couponId);
    }

    public void updateCoupon(Coupon coupon) throws NoSuchCouponException, SQLException, SystemMalfunctionException {
        for (Coupon companyDaoCoupon : companyDao.getCoupons(coupon.getCompanyId())) {
            if (coupon.getTitle().equals(companyDaoCoupon.getTitle()) &&
                    companyDaoCoupon.getId() == coupon.getId())
                couponDao.updateCoupon(coupon);
        }
    }

    public Coupon getCoupon(long couponId) throws NoSuchCouponException, SQLException, SystemMalfunctionException {
        return couponDao.getCoupon(couponId);
    }

    public Collection<Coupon> getAllCoupons() throws SystemMalfunctionException {
        return companyDao.getCoupons(companyId);
    }

    public Collection<Coupon> getCouponsByCategory(int category) throws NoSuchCouponException, SQLException, SystemMalfunctionException {
        Collection<Coupon> allCoupons = couponDao.getAllCoupons(category);
        ArrayList<Coupon> companyCouponsByCategory = null;
        for (Coupon coupon : allCoupons) {
            if (coupon.getCompanyId() == companyId)
                companyCouponsByCategory.add(coupon);
        }
        return companyCouponsByCategory;
    }

    public Collection<Coupon> getCouponsLowerThanPrice(double price) throws SystemMalfunctionException {
        ArrayList<Coupon> couponsLowerPrice = new ArrayList<>();
        for (Coupon coupon : getAllCoupons()) {
            if (coupon.getPrice() < price)
                couponsLowerPrice.add(coupon);
        }
        return couponsLowerPrice;
    }

    public Collection<Coupon> getCouponsBeforeEndDate(Date endDate) throws SystemMalfunctionException {
        ArrayList<Coupon> couponsBeforeEndDate = new ArrayList<>();
        for (Coupon coupon : getAllCoupons()) {
            if (coupon.getEndDate().before(endDate))
                couponsBeforeEndDate.add(coupon);
        }
        return couponsBeforeEndDate;
    }
}
