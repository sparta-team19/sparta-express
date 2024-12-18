package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class ShipmentInterhubRouteRes {

    UUID originHubId;
    String originHubAddress;
    UUID destinationHubId;
    String destinationHubAddress;
    int distanceKm;
    int estimatedMinutes;
    InterhubRouteStatus status;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;

    public static ShipmentInterhubRouteRes from(InterhubRoute interhubRoute) {

        return ShipmentInterhubRouteRes.builder()
                .originHubId(interhubRoute.getOriginHubId())
                .originHubAddress(interhubRoute.getOriginHub().getAddress())
                .destinationHubId(interhubRoute.getDestinationHubId())
                .destinationHubAddress(interhubRoute.getDestinationHub().getAddress())
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
