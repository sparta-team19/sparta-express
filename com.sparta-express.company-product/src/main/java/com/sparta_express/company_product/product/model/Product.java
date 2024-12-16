package com.sparta_express.company_product.product.model;


import com.sparta_express.company_product.common.BaseEntity;
import com.sparta_express.company_product.company.model.Company;
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

    @Column(nullable = false)
    private UUID hubId;

    private String name;

    private Integer price;

    @Builder
    private Product(Company company, UUID hubId, String name, Integer price) {
        this.company = company;
        this.hubId = hubId;
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, Integer price, Company company, UUID hubId) {
        return builder()
                .company(company)
                .hubId(hubId)
                .name(name)
                .price(price)
                .build();
    }

    public void update(String name, Integer price) {
        if (name != null) this.name = name;
        if (price != null) this.price = price;
    }

}
