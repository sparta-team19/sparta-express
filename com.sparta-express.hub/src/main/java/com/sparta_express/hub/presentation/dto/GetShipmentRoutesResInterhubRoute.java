package com.sparta_express.hub.presentation.dto;

import com.sparta_express.hub.application.InterhubRouteStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class GetShipmentRoutesResInterhubRoute {

    @NonNull UUID originHubId;
    @NonNull UUID destinationHubId;
    @NonNull int distanceKm;
    @NonNull int estimatedMinutes;
    @NonNull InterhubRouteStatus status;
    @NonNull LocalDateTime createdAt;
    @NonNull long createdBy;//todo  멘토님 문의후 loginId, pk중 결정
    @NonNull LocalDateTime updatedAt;
    @NonNull long updatedBy;

    public static GetShipmentRoutesResInterhubRoute from(InterhubRoute interhubRoute) {

        return GetShipmentRoutesResInterhubRoute.builder()
                .originHubId(interhubRoute.getOriginHubId())
                .destinationHubId(interhubRoute.getDestinationHubId())
                .distanceKm(interhubRoute.getDistanceKm())
                .estimatedMinutes(interhubRoute.getEstimatedMinutes())
                .status(interhubRoute.getStatus())
                .createdAt(interhubRoute.getCreatedAt())
                .createdBy(interhubRoute.getCreatedBy())
                .updatedAt(interhubRoute.getUpdatedAt())
                .updatedBy(interhubRoute.getUpdatedBy())
                .build();
    }

}
