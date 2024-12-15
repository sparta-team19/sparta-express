package com.sparta_express.order_shipment.stock.dto;


import com.sparta_express.order_shipment.stock.model.Stock;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StockResponse {

    private UUID id;
    private String productName;
    private String hubName;
    private Integer stockQuantity;

    private StockResponse(UUID id, String productName, String hubName, Integer stockQuantity) {
        this.id = id;
        this.productName = productName;
        this.hubName = hubName;
        this.stockQuantity = stockQuantity;
    }

    public static StockResponse from(Stock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getProduct().getName(),
                stock.getHub().getName(),
                stock.getStockQuantity()
        );
    }

}