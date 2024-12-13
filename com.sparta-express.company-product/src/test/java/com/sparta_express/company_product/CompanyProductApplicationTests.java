package com.sparta_express.company_product;

import com.sparta_express.company_product.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyProductApplicationTests {

	@Autowired
	private ProductRepository productRepository;

    @Test
	void contextLoads() {
	}

}
