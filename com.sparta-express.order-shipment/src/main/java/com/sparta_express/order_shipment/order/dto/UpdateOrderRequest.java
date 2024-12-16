package com.sparta_express.order_shipment.order.dto;

import com.sparta_express.order_shipment.order.model.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateOrderRequest {

    private Integer quantity;

    private LocalDateTime dueDate;

    private String requestDetails;

    private OrderStatus orderStatus;

}
