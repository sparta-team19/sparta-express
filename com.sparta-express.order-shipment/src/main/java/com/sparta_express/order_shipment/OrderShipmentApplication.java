package com.sparta_express.order_shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class OrderShipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderShipmentApplication.class, args);
	}

}
