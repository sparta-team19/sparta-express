package com.sparta_express.order_shipment.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private String name;
    private Integer price;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getName(),
                product.getPrice()
        );
    }
}
