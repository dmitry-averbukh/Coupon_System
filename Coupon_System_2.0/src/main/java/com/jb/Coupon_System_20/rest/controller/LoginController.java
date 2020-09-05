package com.jb.Coupon_System_20.rest.controller;

import com.jb.Coupon_System_20.rest.*;
import com.jb.Coupon_System_20.rest.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {
    private static final int LENGTH_TOKEN = 15;
    private final Map<String, ClientSession> tokensMap;
    private final CompanySystem companySystem;
    private final CustomerSystem customerSystem;
    private final AdminSystem adminSystem;

    public LoginController(@Qualifier("tokens") Map<String, ClientSession> tokensMap,
                           CompanySystem companySystem, CustomerSystem customerSystem, AdminSystem adminSystem) {
        this.tokensMap = tokensMap;
        this.companySystem = companySystem;
        this.customerSystem = customerSystem;
        this.adminSystem = adminSystem;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws InvalidLoginException {
        try {
            ClientSession session = companySystem.createSession(email, password);

            String token = generateToken();

            session.setIdentifier(1);

            tokensMap.put(token, session);

            return ResponseEntity.ok(token);

        } catch (InvalidLoginException e) {
            try {
                ClientSession session = customerSystem.createSession(email, password);

                String token = generateToken();

                session.setIdentifier(2);

                tokensMap.put(token, session);

                return ResponseEntity.ok(token);


            } catch (InvalidLoginException e2) {
                try {
                    ClientSession session = adminSystem.createSession(email, password);

                    String token = generateToken();

                    session.setIdentifier(3);

                    tokensMap.put(token, session);

                    return ResponseEntity.ok(token);


                } catch (InvalidLoginException e3) {
                    throw new InvalidLoginException();
                }
            }
        }
    }

    private String generateToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, LENGTH_TOKEN);
    }
}
