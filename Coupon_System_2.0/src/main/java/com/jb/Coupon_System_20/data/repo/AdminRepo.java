package com.jb.Coupon_System_20.data.repo;

import com.jb.Coupon_System_20.data.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin,Long> {
    Optional<Admin> findAdminByEmailAndPassword(String email, String password);
}
