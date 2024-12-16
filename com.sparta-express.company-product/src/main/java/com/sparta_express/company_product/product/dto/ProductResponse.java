package com.sparta_express.company_product.product.dto;

import com.sparta_express.company_product.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Integer price;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCompany().getId(),
                product.getHubId(),
                product.getName(),
                product.getPrice()
        );
    }

}
