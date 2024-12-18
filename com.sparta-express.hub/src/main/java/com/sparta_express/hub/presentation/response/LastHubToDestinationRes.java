package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.LastHubToDestination;
import lombok.Builder;
import lombok.Value;

@Value
public class LastHubToDestinationRes {

    int distanceKm;
    int estimatedMinutes;

    @Builder
    public LastHubToDestinationRes(int distanceKm, int estimatedMinutes) {

        assert (distanceKm > 0 && estimatedMinutes > 0);

        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
    }

    public static LastHubToDestinationRes from(LastHubToDestination lastHubToDestination) {
        return LastHubToDestinationRes.builder()
                .distanceKm(lastHubToDestination.getDistanceKm())
                .estimatedMinutes(lastHubToDestination.getEstimatedMinutes())
                .build();
    }
}
