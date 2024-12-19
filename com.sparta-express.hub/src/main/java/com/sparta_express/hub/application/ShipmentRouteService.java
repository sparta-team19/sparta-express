package com.sparta_express.hub.application;

import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.domain.ShipmentRouteDomainService;
import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.LastHubToDestination;
import com.sparta_express.hub.infrastructure.map.MapApiInfra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShipmentRouteService {

    private final ShipmentRouteDomainService shipmentRouteDomainService;
    private final MapApiInfra mapApi;


//    @Cacheable(cacheNames = "shipmentInterhubRoutes",
//            key = "#originHubId.toString() + #destinationHubId.toString()")
    public final List<InterhubRoute> findShipmentInterhubRoutes(UUID originHubId,
                                                                UUID destinationHubId) {

        return shipmentRouteDomainService.findShipmentInterhubRoutes(
                originHubId, destinationHubId
        );
    }

    public final LastHubToDestination findFinalHubToDestination(String destinationAddress) {

        return shipmentRouteDomainService.findFinalHubToDestination(
                findGeometryPosition(destinationAddress)
        );
    }

    protected final Hub findNearestHub(String address) {

        return shipmentRouteDomainService.findNearestHub(
                findGeometryPosition(address)
        );
    }

    protected final Position findGeometryPosition(String address) {

        return mapApi.searchPosition(address);
    }
}
