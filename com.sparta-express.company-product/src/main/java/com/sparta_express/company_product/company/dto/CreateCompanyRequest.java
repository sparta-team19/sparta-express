package com.sparta_express.company_product.company.dto;

import com.sparta_express.company_product.company.model.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateCompanyRequest {

    @NotBlank(message = "회사명은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "회사 주소는 필수 입력 항목입니다.")
    private String address;

    @NotNull(message = "회사 종류는 필수 입력 항목입니다.")
    private CompanyType companyType;

    @NotNull(message = "허브 ID는 필수 입력 항목입니다.")
    private UUID hubId;

}
