package com.sparta_express.hub.domain.service;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
class HubRouteInfo {

    private final int distance;
    private final List<InterhubRoute> interhubRoutes;
    private final UUID hubId;

    public static HubRouteInfo initMemo(Hub hub) {
        return new HubRouteInfo(Integer.MAX_VALUE, new ArrayList<>(), hub.getId());
    }

    public static HubRouteInfo initQueue(InterhubRoute routeFromOrigin) {
        return new HubRouteInfo(routeFromOrigin.getDistanceKm(), List.of(routeFromOrigin), routeFromOrigin.getDestinationHubId());
    }
}
