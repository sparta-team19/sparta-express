package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class InterhubRouteResOfGetShipmentRoutes {

    UUID originHubId;
    UUID destinationHubId;
    int distanceKm;
    int estimatedMinutes;
    InterhubRouteStatus status;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;

    public static InterhubRouteResOfGetShipmentRoutes from(InterhubRoute interhubRoute) {

        return InterhubRouteResOfGetShipmentRoutes.builder()
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
