package com.sparta_express.company_product.company.dto;

import com.sparta_express.company_product.company.model.CompanyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCompanyRequest {

    private String name;

    private String address;

    private CompanyType companyType;

}