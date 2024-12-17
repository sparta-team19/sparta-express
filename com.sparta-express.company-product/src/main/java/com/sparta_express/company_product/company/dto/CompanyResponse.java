package com.sparta_express.company_product.company.dto;

import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.company.model.CompanyType;
import com.sparta_express.company_product.product.model.Product;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyResponse {

    private UUID id;

    private String name;

    private String address;

    private CompanyType companyType;

    private UUID hubId;

    private List<ProductDto> products;

    @Builder
    private CompanyResponse(UUID id, String name, String address, CompanyType companyType, UUID hubId, List<ProductDto> products) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.companyType = companyType;
        this.hubId = hubId;
        this.products = products;
    }

    public static CompanyResponse from(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .companyType(company.getCompanyType())
                .hubId(company.getHubId())
                .products(company.getProducts().stream().map(ProductDto::from).collect(Collectors.toList()))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProductDto {
        private UUID id;
        private UUID hubId;
        private String name;
        private Integer price;

        public static ProductDto from(Product product) {
            return new ProductDto(
                    product.getId(),
                    product.getHubId(),
                    product.getName(),
                    product.getPrice());
        }
    }

}
