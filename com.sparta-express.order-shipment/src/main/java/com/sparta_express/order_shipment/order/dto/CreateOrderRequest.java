package com.sparta_express.order_shipment.order.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CreateOrderRequest {

    private UUID productId;
    private Long requesterId;
    private Long receiverId;
    private Integer quantity;
    private LocalDateTime dueDate;
    private String requestDetails;

}
