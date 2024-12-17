package com.sparta_express.hub.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HubToDestinationDTO {

    int distanceKm;
    int estimatedMinutes;

}
