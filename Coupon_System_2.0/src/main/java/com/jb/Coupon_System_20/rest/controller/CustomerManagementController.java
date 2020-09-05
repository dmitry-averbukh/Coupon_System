package com.jb.Coupon_System_20.rest.controller;

import com.jb.Coupon_System_20.data.entity.Coupon;
import com.jb.Coupon_System_20.rest.ClientSession;
import com.jb.Coupon_System_20.rest.ex.TokenTimeOutException;
import com.jb.Coupon_System_20.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerManagementController {
    private final CustomerService customerService;
    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public CustomerManagementController(CustomerService customerService,
                                        @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.customerService = customerService;
        this.tokensMap = tokensMap;
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> GetCustomerCoupons(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (clientSession.getIdentifier() == 2) {

            clientSession.access();
            Optional<List<Coupon>> customerCoupons = customerService.getAllCustomerCoupons(clientSession.getClientId());

            if (customerCoupons != null && !customerCoupons.isEmpty()) {
                throw new TokenTimeOutException();
            }

            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
