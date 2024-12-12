package com.sparta_express.hub.application.dto;


import lombok.Builder;
import lombok.Getter;


@Getter
public class FinalHubToDestination {

    private final int distanceKm;
    private final int estimatedMinutes;

    @Builder
    public FinalHubToDestination(int distanceKm, int estimatedMinutes) {
        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
    }
}
