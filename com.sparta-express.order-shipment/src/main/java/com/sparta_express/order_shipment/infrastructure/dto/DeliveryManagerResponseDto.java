package com.sparta_express.order_shipment.infrastructure.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryManagerResponseDto {
    private UUID deliveryManagerId;
    private String type;
    private int deliverySequence;
}
