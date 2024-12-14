package com.sparta_express.company_product.company.dto;

import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.company.model.CompanyType;
import com.sparta_express.company_product.external.Hub;
import com.sparta_express.company_product.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CompanyResponse {

    private UUID id;

    private String name;

    private String address;

    private CompanyType companyType;

    private Hub hub;

    private List<Product> products;

    public static CompanyResponse from(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getAddress(),
                company.getCompanyType(),
                company.getHub(),
                null
        );
    }

}