package com.sparta_express.order_shipment.infrastructure.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
public class CompanyResponseDto {
    private UUID id;
    private String name;
    private String address;
    private String companyType;
    private UUID hubId;
    private List<ProductDto> products;

    @Getter
    @ToString
    public static class ProductDto {
        private UUID id;
        private UUID hubId;
        private String name;
        private Integer price;
    }

}
