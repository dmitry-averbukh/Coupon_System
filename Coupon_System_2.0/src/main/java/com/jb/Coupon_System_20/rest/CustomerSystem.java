package com.jb.Coupon_System_20.rest;

import com.jb.Coupon_System_20.data.entity.Customer;
import com.jb.Coupon_System_20.data.repo.CustomerRepo;
import com.jb.Coupon_System_20.rest.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerSystem {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerSystem(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public ClientSession createSession(String email, String password) throws InvalidLoginException {
        Optional<Customer> optionalCustomer = customerRepo.findCustomerByEmailAndPassword(email, password);
        if (optionalCustomer.isPresent()) {
            return ClientSession.create(optionalCustomer.get().getId());
        }
        throw new InvalidLoginException();
    }
}
