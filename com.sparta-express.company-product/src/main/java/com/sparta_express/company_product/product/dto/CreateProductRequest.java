package com.sparta_express.company_product.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateProductRequest {

    private UUID companyId;

    private UUID hubId;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String name;

    private Integer price;

}
