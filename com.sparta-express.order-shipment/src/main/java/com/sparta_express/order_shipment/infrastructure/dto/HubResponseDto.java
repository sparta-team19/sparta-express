package com.sparta_express.order_shipment.infrastructure.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class HubResponseDto {
    UUID originHubId;
    UUID destinationHubId;
    int distanceKm;
    int estimatedMinutes;
    String status;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
}

