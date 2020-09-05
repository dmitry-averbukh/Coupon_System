package com.jb.Coupon_System_20.rest;

import com.jb.Coupon_System_20.data.entity.Company;
import com.jb.Coupon_System_20.data.repo.CompanyRepo;
import com.jb.Coupon_System_20.rest.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanySystem {
    private final CompanyRepo companyRepo;

    @Autowired
    public CompanySystem(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    public ClientSession createSession (String email, String password) throws InvalidLoginException {
        Optional<Company> optionalCompany = companyRepo.findCompanyByEmailAndPassword(email, password);
        if (optionalCompany.isPresent()){
            return ClientSession.create(optionalCompany.get().getId());
        }
        throw new InvalidLoginException();
    }
}
