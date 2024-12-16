package com.sparta_express.hub.application;

import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.domain.ShipmentRouteDomainService;
import com.sparta_express.hub.domain.model.FinalHubToDestination;
import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.ShipmentRoute;
import com.sparta_express.hub.infrastructure.map.MapInfra;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShipmentRouteService {

    private final ShipmentRouteDomainService shipmentRouteDomainService;
    private final MapInfra mapApp;


    @Cacheable(cacheNames = "shipmentRoutes",
            key = "#originHubId.toString() + destinationAddress")
    public ShipmentRoute findShipmentRoutes(UUID originHubId, String destinationAddress) {

        return shipmentRouteDomainService.findShipmentRoutes(
                originHubId, findGeometryPosition(destinationAddress)
        );
    }

    @Cacheable(cacheNames = "shipmentInterhubRoutes",
            key = "#originHubId.toString() + #destinationHubId.toString()")
    public final List<InterhubRoute> findShipmentInterhubRoutes(UUID originHubId,
                                                                UUID destinationHubId) {

        return shipmentRouteDomainService.findShipmentInterhubRoutes(
                originHubId, destinationHubId
        );
    }

    @Cacheable(cacheNames = "finalHubToDestinations", key = "#destinationAddress")
    public final FinalHubToDestination findFinalHubToDestination(String destinationAddress) {

        return shipmentRouteDomainService.findFinalHubToDestination(
                findGeometryPosition(destinationAddress)
        );
    }

    @Cacheable(cacheNames = "nearestHubs", key = "#address")
    public final Hub findNearestHub(String address) {

        return shipmentRouteDomainService.findNearestHub(
                findGeometryPosition(address)
        );
    }

    @Cacheable(cacheNames = "addressGeometryPositions", key = "#address")
    protected final Position findGeometryPosition(String address) {

        return mapApp.searchPosition(address);
    }
}
