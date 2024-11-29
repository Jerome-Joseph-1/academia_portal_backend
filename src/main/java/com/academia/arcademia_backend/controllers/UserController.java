package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.AuthRequest;
import com.academia.arcademia_backend.entity.UserInfo;
import com.academia.arcademia_backend.services.UserInfoService;
import com.academia.arcademia_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String test() {
        return "Unsecured endpoint access: Success";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

//    @PostMapping("/generateToken")
//    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(authRequest.getUsername());
//        } else {
//            throw new UsernameNotFoundException("Invalid user request!");
//        }
//    }
}
