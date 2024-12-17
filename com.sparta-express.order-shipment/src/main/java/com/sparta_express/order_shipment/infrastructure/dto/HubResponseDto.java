package com.sparta_express.order_shipment.infrastructure.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class HubResponseDto {
    UUID originHubId;
    UUID destinationHubId;
    int distanceKm;
    int estimatedMinutes;
}

