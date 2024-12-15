package com.sparta_express.company_product;

import com.netflix.discovery.converters.Auto;
import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.company.model.CompanyType;
import com.sparta_express.company_product.company.repository.CompanyRepository;
import com.sparta_express.company_product.external.*;
import com.sparta_express.company_product.product.model.Product;
import com.sparta_express.company_product.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class CompanyProductApplicationTests {

	@Autowired
	private HubRepository hubRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CompanyRepository companyRepository;

    @Autowired
    private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		Hub hub = hubRepository.findById(UUID.fromString("efbfddaa-cca1-4a15-9687-caa8b0d704c0")).orElseThrow();
		Company company = companyRepository.findById(UUID.fromString("22cf0d15-8941-41e0-900f-ad68cb9a9aec")).orElseThrow();
		Product product = productRepository.findById(UUID.fromString("0c33d14e-ee47-41d3-ae5d-4b8b6bf5f785")).orElseThrow();
		User user = userRepository.findById(1L).orElseThrow();

		Order order = Order.of(product, user, user, 1, LocalDateTime.now(), "quick");
		orderRepository.save(order);
	}

}
