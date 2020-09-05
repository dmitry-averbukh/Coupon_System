package com.jb.Coupon_System_20.service;

import com.jb.Coupon_System_20.data.entity.Coupon;
import com.jb.Coupon_System_20.data.repo.CouponRepo;
import com.jb.Coupon_System_20.data.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final CouponRepo couponRepo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo, CouponRepo couponRepo) {
        this.customerRepo = customerRepo;
        this.couponRepo = couponRepo;
    }


    @Override
    public Optional<List<Coupon>> getAllCustomerCoupons(long id) {
        Optional<List<Coupon>> allCoupons = couponRepo.findAllCouponsByCustomerId(id);
        if (allCoupons.isPresent())
            return Optional.of(allCoupons.get());
        return Optional.empty();
    }
}
