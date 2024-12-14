package com.sparta_express.company_product.company.dto;

import com.sparta_express.company_product.company.model.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCompanyRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private CompanyType companyType;

    @NotNull
    private String hubId;

}
