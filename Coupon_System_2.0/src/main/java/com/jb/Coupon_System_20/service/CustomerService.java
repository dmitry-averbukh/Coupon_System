package com.jb.Coupon_System_20.service;

import com.jb.Coupon_System_20.data.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<List<Coupon>> getAllCustomerCoupons(long id);
}
