package com.sparta_express.hub.presentation.controller;

import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;


@Value
@Builder
public class GetInterhubRouteRes {

    UUID id;
    UUID originHubId;
    UUID destinationHubId;
    int distanceKm;
    int estimatedMinutes;
    InterhubRouteStatus status;

    public static GetInterhubRouteRes from(InterhubRoute interhubRoute) {
        return GetInterhubRouteRes.builder()
                .id(interhubRoute.getId())
                .originHubId(interhubRoute.getOriginHub().getId())
                .destinationHubId(interhubRoute.getDestinationHubId())
                .distanceKm(interhubRoute.getDistanceKm())
                .estimatedMinutes(interhubRoute.getEstimatedMinutes())
                .status(interhubRoute.getStatus())
                .build();

    }
}
