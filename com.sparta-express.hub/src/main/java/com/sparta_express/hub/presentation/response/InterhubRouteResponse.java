package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.InterhubRoute;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;


@Value
@Builder
public class InterhubRouteResponse {

    UUID originHubId;
    UUID destinationHubId;
    int distanceKm;
    int estimatedMinutes;

    public static InterhubRouteResponse from(InterhubRoute interhubRoute) {

        return InterhubRouteResponse.builder()
                .originHubId(interhubRoute.getOriginHubId())
                .destinationHubId(interhubRoute.getDestinationHubId())
                .distanceKm(interhubRoute.getDistanceKm())
                .estimatedMinutes(interhubRoute.getEstimatedMinutes())
                .build();
    }

}
