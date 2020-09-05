package com.jb.Coupon_System_20.rest;

import com.jb.Coupon_System_20.data.entity.Admin;
import com.jb.Coupon_System_20.data.repo.AdminRepo;
import com.jb.Coupon_System_20.rest.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminSystem {
    private final AdminRepo adminRepo;

    @Autowired
    public AdminSystem(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }
    public ClientSession createSession (String email, String password) throws InvalidLoginException {
        Optional<Admin> optionalAdmin = adminRepo.findAdminByEmailAndPassword(email, password);
        if (optionalAdmin.isPresent()){
            return ClientSession.create(optionalAdmin.get().getId());
        }
        throw new InvalidLoginException();
    }
}
