package com.example.microservices_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MicroservicesAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesAuthApplication.class, args);
	}

}
