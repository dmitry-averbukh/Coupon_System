package com.jb.Coupon_System_20.service;

import com.jb.Coupon_System_20.data.entity.Company;
import com.jb.Coupon_System_20.data.entity.Coupon;
import com.jb.Coupon_System_20.data.repo.CompanyRepo;
import com.jb.Coupon_System_20.data.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    private final CouponRepo couponRepo;


    @Autowired
    public CompanyServiceImpl(CompanyRepo companyRepo, CouponRepo couponRepo) {
        this.companyRepo = companyRepo;
        this.couponRepo = couponRepo;
    }

    @Override
    public Optional<Coupon> addCoupon(Coupon coupon) {
        return Optional.of(couponRepo.save(coupon));
    }

    @Override
    public Optional<Coupon> removeCoupon(long id) {
        Optional<Coupon> optionalCoupon = couponRepo.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            couponRepo.deleteById(id);
            return Optional.of(coupon);
        }
        return Optional.empty();
    }

    @Override
    public Boolean couponCheck(long id) {
        Optional<Coupon> byId = couponRepo.findById(id);
        return byId.isPresent();
    }

    @Override
    public Boolean companyCouponCheck(long couponId, long companyId) {
        Optional<Coupon> optionalCoupon = couponRepo.findById(couponId);
        if (optionalCoupon.isPresent())
            return companyId == optionalCoupon.get().getCompany().getId();
        return false;
    }

    @Override
    public void updateCoupon(Coupon coupon) {
        Coupon couponToUpdate = couponRepo.getOne(coupon.getId());
        Coupon updatedCoupon = new Coupon();
        updatedCoupon.setId(coupon.getId());
        updatedCoupon.setCompany(coupon.getCompany());
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

        couponRepo.save(updatedCoupon);
    }

    @Override
    public Optional<Coupon> getCouponById(long id) {
        Optional<Coupon> optCoupon = couponRepo.findById(id);
        if (optCoupon.isPresent()) {
            Coupon coupon = optCoupon.get();
            return Optional.of(coupon);
        }
        return Optional.empty();
    }


    @Override
    public Optional<List<Coupon>> getAllCompanyCoupons(long id) {
        if (couponRepo.findAllCouponsByCompanyId(id).isPresent())
            return couponRepo.findAllCouponsByCompanyId(id);
        return Optional.empty();
    }

    @Override
    public Optional<Company> getCompanyByEmailAndPassword(String email, String password) {
        return companyRepo.findCompanyByEmailAndPassword(email, password);
    }

    @Override
    public Company getCompanyById(long id) {
        Optional<Company> optionalCompany = companyRepo.findById(id);
        return optionalCompany.orElse(null);
    }
}
