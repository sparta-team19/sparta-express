package com.sparta_express.hub.domain.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HubToDestinationRoute {

    int distanceKm;
    int estimatedMinutes;

}