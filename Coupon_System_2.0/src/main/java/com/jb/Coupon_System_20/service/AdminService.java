package com.jb.Coupon_System_20.service;

import com.jb.Coupon_System_20.data.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Optional<List<Coupon>> getAllCoupons();

    Optional<Coupon> addCoupon(Coupon coupon);

    Optional<Coupon> deleteCoupon(long id);

    Optional<Coupon> getCouponById(long id);

    Optional<Coupon> updateCoupon(Coupon coupon);

    Optional<List<Coupon>> getAllCompanyCoupons(long id);
}
