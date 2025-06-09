package com.besstore.userCartService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserCartServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserCartServiceApp.class, args);

//        todo: tests
//        todo: docs
//        todo: CI/CD
    }
}