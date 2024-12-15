package com.sparta_express.order_shipment.order.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CreateOrderRequest {

    @NotNull(message = "상품 ID는 필수 항목입니다.")
    private UUID productId;

    @NotNull(message = "요청자 ID는 필수 항목입니다.")
    private Long requesterId;

    @NotNull(message = "수령자 ID는 필수 항목입니다.")
    private Long receiverId;

    @NotNull(message = "수량은 필수 항목입니다.")
    @Positive(message = "수량은 0보다 큰 값이어야 합니다.")
    private Integer quantity;

    @NotNull(message = "배송일자는 필수 항목입니다.")
    @FutureOrPresent(message = "배송일자는 현재 시점 또는 미래여야 합니다.")
    private LocalDateTime dueDate;

    private String requestDetails;

}
