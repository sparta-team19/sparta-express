package com.sparta_express.order_shipment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta_express.order_shipment.application.dto.OrderDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "ORDER_REQUESTER_ID_EMPTY")
    private UUID requesterId;

    @NotNull(message = "ORDER_RECEIVER_ID_EMPTY")
    private UUID receiverId;

    @NotNull(message = "ORDER_PRODUCT_ID_EMPTY")
    private UUID productId;

    @NotNull(message = "ORDER_QUANTITY_EMPTY")
    @Positive(message = "ORDER_QUANTITY_INVALID")
    private Integer quantity;

    @NotBlank(message = "ORDER_DELIVERY_ADDRESS_EMPTY")
    private String deliveryAddress;

    @NotBlank(message = "ORDER_RECIPIENT_NAME_EMPTY")
    private String recipientName;

    private String recipientSlackId;

    @NotNull(message = "ORDER_DUE_DATE_EMPTY")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate dueDate;

    private String requestDetails;

    public OrderDto toDto() {
        return OrderDto.of(
                this.requesterId,
                this.receiverId,
                this.productId,
                this.quantity,
                this.deliveryAddress,
                this.recipientName,
                this.recipientSlackId,
                this.dueDate,
                this.requestDetails
        );
    }

}
