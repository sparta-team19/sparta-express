package com.sparta_express.order_shipment.stock.dto;

import com.sparta_express.order_shipment.external.Hub;
import com.sparta_express.order_shipment.external.Product;
import lombok.Getter;

@Getter
public class UpdateStockRequest {

    private Product product;
    private Hub hub;
    private Integer stockQuantity;

    public UpdateStockRequest(Product product, Hub hub, Integer stockQuantity) {
        this.product = product;
        this.hub = hub;
        this.stockQuantity = stockQuantity;
    }

}