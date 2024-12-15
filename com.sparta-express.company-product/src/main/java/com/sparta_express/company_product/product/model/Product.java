package com.sparta_express.company_product.product.model;


import com.sparta_express.company_product.common.BaseEntity;
import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.external.Hub;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private Product(Company company, Hub hub, String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, Integer price, Company company, Hub hub) {
        return builder()
                .company(company)
                .hub(hub)
                .name(name)
                .price(price)
                .build();
    }

    public void update(String name, Integer price) {
        if (name != null) this.name = name;
        if (price != null) this.price = price;
    }

}
