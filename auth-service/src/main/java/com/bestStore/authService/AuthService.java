package com.bestStore.authService;

import com.beststore.rest.enable.EnableRestSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRestSecurity
public class AuthService {

    public static void main(String[] args) {
        SpringApplication.run(AuthService.class, args);

    }

}
