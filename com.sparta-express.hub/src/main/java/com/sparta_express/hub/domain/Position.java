package com.sparta_express.hub.domain;


import com.sparta_express.hub.common.utils.DistanceCalculator;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Position {
    double latitude;
    double longitude;

    public double distance(Position other) {

        return DistanceCalculator.distanceBetween(
                latitude, longitude, other.latitude, other.longitude
        );
    }
}
