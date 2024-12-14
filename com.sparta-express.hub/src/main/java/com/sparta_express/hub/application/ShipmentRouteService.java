package com.sparta_express.hub.application;

import com.sparta_express.hub.domain.model.ShipmentRoute;
import com.sparta_express.hub.domain.service.ShipmentRouteDomainService;
import com.sparta_express.hub.infrastructure.repository.HubRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentRouteService {

    private final ShipmentRouteDomainService shipmentRouteDomainService;
    private final HubRepositoryImpl hubRepo;


    public ShipmentRoute findShipmentRoutes(UUID originHubId, String destinationAddress) {

        return shipmentRouteDomainService.findShipmentRoutes(originHubId, destinationAddress);
    }


}
