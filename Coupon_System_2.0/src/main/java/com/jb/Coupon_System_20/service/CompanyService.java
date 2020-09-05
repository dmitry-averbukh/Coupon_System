package com.jb.Coupon_System_20.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jb.Coupon_System_20.data.entity.Company;
import com.jb.Coupon_System_20.data.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    void updateCoupon(Coupon coupon) throws JsonProcessingException;

    Optional<Coupon> addCoupon(Coupon coupon);

    Optional<Coupon> removeCoupon(long id);

    Boolean couponCheck(long id);

    Boolean companyCouponCheck(long couponId, long companyId);

    Optional<Coupon> getCouponById(long id);

    Optional<List<Coupon>> getAllCompanyCoupons(long id);

    Optional<Company> getCompanyByEmailAndPassword(String email, String password);

    Company getCompanyById(long id);


}
