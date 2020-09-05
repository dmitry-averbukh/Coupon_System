package com.jb.Coupon_System_20.data.repo;

import com.jb.Coupon_System_20.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerByEmailAndPassword(String email, String password);
}