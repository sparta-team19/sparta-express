package com.sparta_express.company_product.stock.dto;

import com.sparta_express.company_product.stock.model.Stock;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StockResponse {

    private final UUID id;
    private final UUID productId;
    private final UUID hubId;
    private final Integer stockQuantity;

    private StockResponse(UUID id, UUID productId, UUID hubId, Integer stockQuantity) {
        this.id = id;
        this.productId = productId;
        this.hubId = hubId;
        this.stockQuantity = stockQuantity;
    }

    public static StockResponse from(Stock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getProductId(),
                stock.getHubId(),
                stock.getStockQuantity()
        );
    }

}