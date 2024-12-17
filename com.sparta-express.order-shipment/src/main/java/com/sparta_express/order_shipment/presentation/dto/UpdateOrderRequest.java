package com.sparta_express.order_shipment.presentation.dto;

import com.sparta_express.order_shipment.domain.entity.OrderStatusEnum;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UpdateOrderRequest {

    private Integer quantity;

    private LocalDate dueDate;

    private String requestDetails;

    private OrderStatusEnum orderStatus;

}
