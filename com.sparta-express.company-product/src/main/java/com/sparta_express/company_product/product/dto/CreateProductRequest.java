package com.sparta_express.company_product.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateProductRequest {

    @NotNull(message = "회사 ID는 필수 항목입니다.")
    private UUID companyId;

    @NotNull(message = "허브 ID는 필수 항목입니다.")
    private UUID hubId;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String name;

    @Positive(message = "가격은 0보다 큰 값이어야 합니다.")
    private Integer price;

}
