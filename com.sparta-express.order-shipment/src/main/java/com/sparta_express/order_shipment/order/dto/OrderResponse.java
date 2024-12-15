package com.sparta_express.order_shipment.order.dto;

import com.sparta_express.company_product.external.Order;
import com.sparta_express.order_shipment.external.ProductResponse;
import com.sparta_express.order_shipment.order.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class OrderResponse {

    private UUID id;
    private ProductResponse product;
    private Long requesterId;
    private Long receiverId;
    private Integer quantity;
    private LocalDateTime dueDate;
    private String requestDetails;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 엔티티를 Response DTO로 변환하는 정적 팩토리 메서드
    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .product(ProductResponse.from(order.getProduct()))
                .requesterId(order.getRequester().getId())
                .receiverId(order.getReceiver().getId())
                .quantity(order.getQuantity())
                .dueDate(order.getDueDate())
                .requestDetails(order.getRequestDetails())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}