package com.example.microservices_auth;

import com.bestStore.userService.model.AbstractBasicUser;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.example.microservices_auth.model.MyUserDetailService;
import com.example.microservices_auth.webtoken.JwtService;
import com.example.microservices_auth.webtoken.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

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
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(user.email()));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

}
