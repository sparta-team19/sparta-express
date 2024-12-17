package com.sparta_express.order_shipment.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private UUID requesterId;
    private UUID receiverId;
    private UUID productId;
    private Integer quantity;
    private String deliveryAddress;
    private String recipientName;
    private String recipientSlackId;
    private LocalDate dueDate;
    private String requestDetails;

    @Builder
    private OrderDto(UUID requesterId, UUID receiverId, UUID productId,
                    Integer quantity, String deliveryAddress, String recipientName,
                    String recipientSlackId, LocalDate dueDate, String requestDetails) {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.productId = productId;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
        this.recipientSlackId = recipientSlackId;
        this.dueDate = dueDate;
        this.requestDetails = requestDetails;
    }

    public static OrderDto of(UUID requesterId, UUID receiverId, UUID productId,
                              Integer quantity, String deliveryAddress, String recipientName,
                              String recipientSlackId, LocalDate dueDate, String requestDetails) {
        return OrderDto.builder()
                .requesterId(requesterId)
                .receiverId(receiverId)
                .productId(productId)
                .quantity(quantity)
                .deliveryAddress(deliveryAddress)
                .recipientName(recipientName)
                .recipientSlackId(recipientSlackId)
                .dueDate(dueDate)
                .requestDetails(requestDetails)
                .build();
    }

}
