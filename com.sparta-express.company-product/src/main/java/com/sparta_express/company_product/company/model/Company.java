package com.sparta_express.company_product.company.model;

import com.sparta_express.company_product.common.BaseEntity;
import com.sparta_express.company_product.external.Hub;
import com.sparta_express.company_product.product.model.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_companies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    private String name;

    private String address;

    @Builder
    private Company(String name, String address, CompanyType companyType, Hub hub) {
        this.name = name;
        this.address = address;
        this.companyType = companyType;
        this.hub = hub;
    }

    public static Company of(String name, String address, CompanyType companyType, Hub hub) {
        return builder()
                .name(name)
                .address(address)
                .companyType(companyType)
                .hub(hub)
                .build();
    }

    public void update(String name, String address, CompanyType companyType) {
        if (name != null) this.name = name;
        if (address != null) this.address = address;
        if (companyType != null) this.companyType = companyType;
    }

}