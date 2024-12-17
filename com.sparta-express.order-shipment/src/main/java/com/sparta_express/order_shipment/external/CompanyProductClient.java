package com.sparta_express.order_shipment.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-product", path = "/api/products")
public interface CompanyProductClient {

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable UUID id);

}
