package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.AuthRequest;
import com.academia.arcademia_backend.dto.ResponseWrapper;
import com.academia.arcademia_backend.entity.UserInfo;
import com.academia.arcademia_backend.services.UserInfoService;
import com.academia.arcademia_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dev")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtTokenUtil jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/test")
    public ResponseEntity<ResponseWrapper<String>> test() {
        return new ResponseEntity<>(new ResponseWrapper<>(
                HttpStatus.OK.value(),
                "Unsecured endpoint access: Success",
                null),
                HttpStatus.OK);
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<ResponseWrapper<String>> addNewUser(@RequestBody UserInfo userInfo) {
        String result = service.addUser(userInfo);
        return new ResponseEntity<>(new ResponseWrapper<>(
                HttpStatus.CREATED.value(),
                "User added successfully",
                result),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername());
                return new ResponseEntity<>(new ResponseWrapper<>(
                        HttpStatus.OK.value(),
                        "Authentication successful",
                        token),
                        HttpStatus.OK);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid username or password",
                    null),
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
