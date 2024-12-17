package com.sparta_express.hub.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Getter
@Builder
public class ShipmentRoute {

    private final List<InterhubRoute> interhubRoutes;
    private final FinalHubToDestination finalHubToDestination;
}
