package com.jb.Coupon_System_20.data.repo;

import com.jb.Coupon_System_20.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepo extends JpaRepository<Coupon,Long> {
    Optional<List<Coupon>> findAllCouponsByCompanyId(long id);

    @Query(value = "select c from Coupon as c inner join c.customers as customer where customer.id = :id")
    Optional<List<Coupon>> findAllCouponsByCustomerId( long id);

}

