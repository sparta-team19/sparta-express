package com.sparta_express.hub.domain.model;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
public class FinalHubToDestination {

    private final int distanceKm;
    private final int estimatedMinutes;
    private final UUID finalHubId;

    @Builder
    public FinalHubToDestination(int distanceKm, int estimatedMinutes, UUID finalHubId) {

        assert (distanceKm > 0 && estimatedMinutes > 0);

        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
        this.finalHubId = finalHubId;
    }
}
