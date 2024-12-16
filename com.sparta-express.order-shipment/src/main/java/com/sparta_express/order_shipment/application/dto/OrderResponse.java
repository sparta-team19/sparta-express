package com.sparta_express.order_shipment.application.dto;

import com.sparta_express.order_shipment.domain.entity.Order;
import com.sparta_express.order_shipment.domain.entity.OrderStatusEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class OrderResponse {

    private UUID id;
    private UUID productId;
    private UUID requesterId;
    private UUID receiverId;
    private Integer quantity;
    private LocalDate dueDate;
    private String requestDetails;
    private OrderStatusEnum orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .requesterId(order.getRequesterId())
                .receiverId(order.getReceiverId())
                .quantity(order.getQuantity())
                .dueDate(order.getDueDate())
                .requestDetails(order.getRequestDetails())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}