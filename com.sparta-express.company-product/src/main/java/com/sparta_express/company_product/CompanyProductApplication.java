package com.sparta_express.company_product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class CompanyProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyProductApplication.class, args);
	}

}
