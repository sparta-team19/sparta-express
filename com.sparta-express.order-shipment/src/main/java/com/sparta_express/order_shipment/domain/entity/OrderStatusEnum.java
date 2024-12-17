package com.sparta_express.order_shipment.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum {

    PENDING("PENDING"),
    CANCELED("CANCELED"),
    COMPLETED("COMPLETED");

    private final String OrderStatus;

}
