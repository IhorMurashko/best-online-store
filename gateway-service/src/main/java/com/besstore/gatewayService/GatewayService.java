package com.besstore.gatewayService;


@SpringBootApplication
@EnableGatewaySecurity
public class GatewayService {

	public static void main(String[] args) {
		SpringApplication.run(GatewayService.class, args);
	}

}
