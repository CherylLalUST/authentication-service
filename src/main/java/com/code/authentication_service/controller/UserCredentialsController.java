package com.code.authentication_service.controller;

import com.code.authentication_service.model.UserCredentials;
import com.code.authentication_service.service.JwtService;
import com.code.authentication_service.service.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/library/authentication")
public class UserCredentialsController {
    @Autowired
    JwtService jwtService;

    @Autowired
    private UserCredentialsService userCredService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UserCredentials register(@RequestBody UserCredentials user) {
        return userCredService.register(user);
    }

    @GetMapping("/validatetoken")
    public boolean validateToken(@RequestParam String token) {
        return userCredService.verifyToken(token);
    }

    @PostMapping("/validateuser")
    public String getToken(@RequestBody UserCredentials user) {
        System.out.println("user : " + user);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        System.out.println("authenticated?? : " + authenticate.isAuthenticated());
        if(authenticate.isAuthenticated()) {
            return userCredService.generateToken(user.getUsername());
        }
        return null;
    }
}