package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.FinalHubToDestination;
import lombok.Builder;
import lombok.Value;

@Value
public class FinalHubToDestinationResOfGetShipmentRoutes {

    int distanceKm;
    int estimatedMinutes;

    @Builder
    public FinalHubToDestinationResOfGetShipmentRoutes(int distanceKm, int estimatedMinutes) {

        assert (distanceKm > 0 && estimatedMinutes > 0);

        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
    }

    public static FinalHubToDestinationResOfGetShipmentRoutes from(FinalHubToDestination finalHubToDestination) {
        return FinalHubToDestinationResOfGetShipmentRoutes.builder()
                .distanceKm(finalHubToDestination.getDistanceKm())
                .estimatedMinutes(finalHubToDestination.getEstimatedMinutes())
                .build();
    }
}
