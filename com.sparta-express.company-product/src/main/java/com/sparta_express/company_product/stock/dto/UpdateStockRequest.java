package com.sparta_express.company_product.stock.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateStockRequest {

    private UUID productId;

    private UUID hubId;

    private Integer stockQuantity;

}