package com.sparta_express.company_product.stock.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateStockRequest {

    @NotNull(message = "상품 ID는 필수 항목입니다.")
    private UUID productId;

    @NotNull(message = "허브 ID는 필수 항목입니다.")
    private UUID hubId;

    @NotNull(message = "재고 수량은 필수 항목입니다.")
    @PositiveOrZero(message = "재고 수량은 0 이상이어야 합니다.")
    private Integer stockQuantity;

}