package com.sparta_express.company_product.product.model;


import com.sparta_express.company_product.common.BaseEntity;
import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.external.Hub;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "hub_id")
    private Hub hub;

    private String name;

    private Integer price;

    @Builder
    private Product(String name) {
        this.name = name;
    }

    public static Product of(String name) {
        return builder()
                .name(name)
                .build();
    }

}
