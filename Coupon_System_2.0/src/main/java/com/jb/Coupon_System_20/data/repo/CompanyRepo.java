package com.jb.Coupon_System_20.data.repo;

import com.jb.Coupon_System_20.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Long> {
    Optional<Company> findCompanyByEmailAndPassword(String email, String password);
}
