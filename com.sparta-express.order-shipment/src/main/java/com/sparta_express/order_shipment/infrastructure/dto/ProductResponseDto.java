package com.sparta_express.order_shipment.infrastructure.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class ProductResponseDto {
    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Integer price;
}
