package com.sparta_express.company_product.external;

import com.sparta_express.company_product.common.BaseEntity;
import com.sparta_express.company_product.product.model.Product;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;

    private Integer stockQuantity;

    public Stock(Product product, Hub hub, Integer stockQuantity) {
        this.product = product;
        this.hub = hub;
        this.stockQuantity = stockQuantity;
    }

    public static Stock of(Product product, Hub hub, Integer stockQuantity) {
        return new Stock(product, hub, stockQuantity);
    }

}
