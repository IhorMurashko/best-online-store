package com.example.microservices_auth.controllers;

import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.example.microservices_auth.service.MyUserDetailService;
import com.example.microservices_auth.webtoken.JwtService;
import jakarta.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailService myUserDetailService;

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginCredentialsDto user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.email(), user.password()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken((UserDetails) authentication.getPrincipal());
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
    @GetMapping("/test")
    public String test() {
       return "test passed";
    }
}
