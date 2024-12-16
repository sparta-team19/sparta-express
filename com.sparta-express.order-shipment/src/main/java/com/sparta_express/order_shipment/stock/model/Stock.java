package com.sparta_express.order_shipment.stock.model;

import com.sparta_express.order_shipment.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_stocks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private UUID hubId;

    private Integer stockQuantity;

    public Stock(UUID productId, UUID hubId, Integer stockQuantity) {
        this.productId = productId;
        this.hubId = hubId;
        this.stockQuantity = stockQuantity;
    }

    public static Stock of(UUID productId, UUID hubId, Integer stockQuantity) {
        return new Stock(productId, hubId, stockQuantity);
    }

    public void update(Integer stockQuantity) {
        if (stockQuantity != null) this.stockQuantity = stockQuantity;
    }

}
