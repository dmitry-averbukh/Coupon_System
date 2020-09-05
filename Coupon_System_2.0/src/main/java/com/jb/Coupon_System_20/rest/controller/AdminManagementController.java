package com.jb.Coupon_System_20.rest.controller;


import com.jb.Coupon_System_20.data.entity.Coupon;
import com.jb.Coupon_System_20.rest.ClientSession;
import com.jb.Coupon_System_20.rest.ex.IdNotProvidedException;
import com.jb.Coupon_System_20.rest.ex.TokenTimeOutException;
import com.jb.Coupon_System_20.service.AdminService;
import com.jb.Coupon_System_20.service.CompanyService;
import com.jb.Coupon_System_20.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminManagementController {
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final AdminService adminService;
    private final Map<String, ClientSession> tokensMap;


    public AdminManagementController(CustomerService customerService, CompanyService companyService,
                                     AdminService adminService, Map<String, ClientSession> tokensMap) {
        this.customerService = customerService;
        this.companyService = companyService;
        this.adminService = adminService;
        this.tokensMap = tokensMap;
    }

    @GetMapping("/customer_coupons")
    public ResponseEntity<List<Coupon>> GetCustomerCoupons(@RequestParam String token, @RequestParam long id) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();

        if (clientSession.getIdentifier() == 3) {

            clientSession.access();
            Optional<List<Coupon>> customerCoupons = customerService.getAllCustomerCoupons(id);

            if (customerCoupons.isPresent() && !customerCoupons.isEmpty()) {
                return ResponseEntity.ok(customerCoupons.get());
            }

            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/all_coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();

        if (clientSession.getIdentifier() == 3) {

            clientSession.access();
            Optional<List<Coupon>> customerCoupons = adminService.getAllCoupons();

            if (customerCoupons.isPresent() && !customerCoupons.isEmpty()) {
                return ResponseEntity.ok(customerCoupons.get());
            }

            return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/add_coupon")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon,
                                            @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();

        if (clientSession.getIdentifier() == 3) {

            clientSession.access();
            return ResponseEntity.ok(adminService.addCoupon(coupon).get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/delete_coupon")
    public ResponseEntity<Coupon> deleteCoupons(@RequestParam String token,
                                                @RequestParam long id) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();

        if (clientSession.getIdentifier() == 3) {

            clientSession.access();
            return ResponseEntity.ok(adminService.deleteCoupon(id).get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/update_coupon")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon,
                                               @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();

        if (clientSession.getIdentifier() == 3) {
            clientSession.access();
            adminService.updateCoupon(coupon);
            return ResponseEntity.of(adminService.getCouponById(coupon.getId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/company_coupons")
    public ResponseEntity<List<Coupon>> updateCoupon(@RequestParam long id,
                                                     @RequestParam String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null)
            throw new TokenTimeOutException();

        if (clientSession.getIdentifier() == 3) {
            clientSession.access();
            return ResponseEntity.of(adminService.getAllCompanyCoupons(id));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
