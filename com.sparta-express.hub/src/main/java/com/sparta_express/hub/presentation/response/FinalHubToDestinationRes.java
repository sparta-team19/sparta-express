package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.FinalHubToDestination;
import lombok.Builder;
import lombok.Value;

@Value
public class FinalHubToDestinationRes {

    int distanceKm;
    int estimatedMinutes;

    @Builder
    public FinalHubToDestinationRes(int distanceKm, int estimatedMinutes) {

        assert (distanceKm > 0 && estimatedMinutes > 0);

        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
    }

    public static FinalHubToDestinationRes from(FinalHubToDestination finalHubToDestination) {
        return FinalHubToDestinationRes.builder()
                .distanceKm(finalHubToDestination.getDistanceKm())
                .estimatedMinutes(finalHubToDestination.getEstimatedMinutes())
                .build();
    }
}
