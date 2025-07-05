package com.bestStore.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AppUserService {
    public static void main(String[] args) {
        SpringApplication.run(AppUserService.class, args);
    }
}
