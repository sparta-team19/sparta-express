package com.sparta_express.hub.application;

import com.sparta_express.hub.domain.model.FinalHubToDestination;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.ShipmentRoute;
import com.sparta_express.hub.domain.service.ShipmentRouteDomainService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentRouteService {

    private final ShipmentRouteDomainService shipmentRouteDomainService;


    public ShipmentRoute findShipmentRoutes(UUID originHubId, String destinationAddress) {

        return shipmentRouteDomainService.findShipmentRoutes(
                originHubId, this.findGeometryPoint(destinationAddress)
        );
    }

    public final FinalHubToDestination findFinalHubToDestination(String destinationAddress) {

        return shipmentRouteDomainService.findFinalHubToDestination(
                this.findGeometryPoint(destinationAddress)
        );
    }

//    public final UUID findNearestHubId(String address) {
//
//        return shipmentRouteDomainService.findNearestHub(
//                this.findGeometryPoint(address)
//        );
//    }

    protected final Point findGeometryPoint(String address) {
        //todo: implement
        return null;
    }

    public final List<InterhubRoute> findShipmentInterhubRoutes(UUID originHubId,
                                                                UUID destinationHubId) {

        return shipmentRouteDomainService.findShipmentInterhubRoutes(
                originHubId, destinationHubId
        );
    }
}
