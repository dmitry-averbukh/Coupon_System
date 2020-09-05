package com.jb.Coupon_System_20.service;

import com.jb.Coupon_System_20.data.entity.Coupon;
import com.jb.Coupon_System_20.data.repo.CouponRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final CouponRepo couponRepo;

    public AdminServiceImpl(CouponRepo couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Override
    public Optional<List<Coupon>> getAllCoupons() {
        Optional<List<Coupon>> allCoupons = Optional.of(couponRepo.findAll());
        if (allCoupons.isPresent())
            return Optional.of(allCoupons.get());
        return Optional.empty();
    }

    @Override
    public Optional<Coupon> addCoupon(Coupon coupon) {
        return Optional.of(couponRepo.save(coupon));
    }

    @Override
    public Optional<Coupon> deleteCoupon(long id) {
        Optional<Coupon> optionalCoupon = couponRepo.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            couponRepo.deleteById(id);
            return Optional.of(coupon);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Coupon> getCouponById(long id) {
        return couponRepo.findById(id);
    }

    @Override
    public Optional<Coupon> updateCoupon(Coupon coupon) {
        Coupon couponToUpdate = couponRepo.findById(coupon.getId()).get();
        Coupon updatedCoupon = new Coupon();
        updatedCoupon.setId(coupon.getId());
        if (coupon.getCompany() != null)
            updatedCoupon.setCompany(coupon.getCompany());
        else
            updatedCoupon.setCompany(couponToUpdate.getCompany());

        if (coupon.getAmount() != 0)
            updatedCoupon.setAmount(coupon.getAmount());
        else
            updatedCoupon.setAmount(couponToUpdate.getAmount());

        if (coupon.getCategory() != 0)
            updatedCoupon.setCategory(coupon.getCategory());
        else
            updatedCoupon.setCategory(couponToUpdate.getCategory());

        if (!coupon.getDescription().isEmpty())
            updatedCoupon.setDescription(coupon.getDescription());
        else
            updatedCoupon.setDescription(couponToUpdate.getDescription());

        if (coupon.getEndDate() != null)
            updatedCoupon.setEndDate(coupon.getEndDate());
        else
            updatedCoupon.setEndDate(couponToUpdate.getEndDate());

        if (coupon.getStartDate() != null)
            updatedCoupon.setStartDate(coupon.getStartDate());
        else
            updatedCoupon.setStartDate(couponToUpdate.getStartDate());

        if (!coupon.getImgUrl().isEmpty())
            updatedCoupon.setImgUrl(coupon.getImgUrl());
        else
            updatedCoupon.setImgUrl(couponToUpdate.getImgUrl());

        if (coupon.getPrice() != 0)
            updatedCoupon.setPrice(coupon.getPrice());
        else
            updatedCoupon.setPrice(couponToUpdate.getPrice());

        if (!coupon.getTitle().isEmpty())
            updatedCoupon.setTitle(coupon.getTitle());
        else
            updatedCoupon.setTitle(couponToUpdate.getTitle());

        return Optional.of(couponRepo.save(updatedCoupon));
    }

    @Override
    public Optional<List<Coupon>> getAllCompanyCoupons(long id) {
        return couponRepo.findAllCouponsByCompanyId(id);
    }
}
