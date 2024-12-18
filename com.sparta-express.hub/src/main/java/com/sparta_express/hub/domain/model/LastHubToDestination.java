package com.sparta_express.hub.domain.model;


import com.sparta_express.hub.Hub;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Getter
public class LastHubToDestination {

    private final int distanceKm;
    private final int estimatedMinutes;
    private final Hub lastHub;

    @Builder
    public LastHubToDestination(int distanceKm, int estimatedMinutes, Hub lastHub) {

        assert (distanceKm > 0 && estimatedMinutes > 0);

        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
        this.lastHub = lastHub;
    }

    public UUID getLastHubId() {
        return lastHub.getId();
    }
}
