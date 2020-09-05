package com.jb.Coupon_System_20.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jb.Coupon_System_20.data.entity.Coupon;
import com.jb.Coupon_System_20.rest.ClientSession;
import com.jb.Coupon_System_20.rest.ex.TokenTimeOutException;
import com.jb.Coupon_System_20.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompanyManagementController {
    private final CompanyService companyService;
    private final Map<String, ClientSession> tokensMap;
    
    @Autowired
    public CompanyManagementController(CompanyService companyService,
                                       @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.companyService = companyService;
        this.tokensMap = tokensMap;
    }

    @PostMapping("/add_coupon")
    public ResponseEntity<Coupon> addNewCoupon(@RequestBody Coupon coupon,
                                               @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();
        if (clientSession.getIdentifier() == 1) {
            clientSession.access();
            coupon.setCompany(companyService.getCompanyById(clientSession.getClientId()));
            return ResponseEntity.ok(companyService.addCoupon(coupon).get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/delete_coupon")
    public ResponseEntity<Coupon> deleteCoupon(long id, @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();
        if (clientSession.getIdentifier() == 1) {
            if (companyService.companyCouponCheck(id, clientSession.getClientId()))
                return ResponseEntity.ok(companyService.removeCoupon(id).get());
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/update_coupon")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon,
                                               @RequestParam String token) throws JsonProcessingException {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();
        if (clientSession.getIdentifier() == 1) {
            clientSession.access();
            coupon.setCompany(companyService.getCompanyById(clientSession.getClientId()));

            if (companyService.companyCouponCheck(coupon.getId(), coupon.getCompany().getId())) {

                companyService.updateCoupon(coupon);
                return ResponseEntity.of(companyService.getCouponById(coupon.getId()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/all_coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token) {

        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();

        if (clientSession.getIdentifier() == 1) {
            clientSession.access();
            return ResponseEntity.of(companyService.getAllCompanyCoupons(clientSession.getClientId()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}