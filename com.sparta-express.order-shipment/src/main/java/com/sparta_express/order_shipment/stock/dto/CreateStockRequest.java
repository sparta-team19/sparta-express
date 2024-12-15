package com.sparta_express.order_shipment.stock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
public class CreateStockRequest {

    private UUID productId;
    private UUID hubId;
    private Integer stockQuantity;

    public CreateStockRequest(UUID productId, UUID hubId, Integer stockQuantity) {
        this.productId = productId;
        this.hubId = hubId;
        this.stockQuantity = stockQuantity;
    }

}