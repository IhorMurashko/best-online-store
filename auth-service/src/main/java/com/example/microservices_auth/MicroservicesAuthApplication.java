package com.example.microservices_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
<<<<<<< HEAD
=======
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
>>>>>>> main
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
<<<<<<< HEAD
=======
@EnableWebSecurity
>>>>>>> main
public class MicroservicesAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesAuthApplication.class, args);

	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
