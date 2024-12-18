package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.ShipmentRoute;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class GetShipmentRoutesResponse {

    List<ShipmentInterhubRouteRes> interhubRoutes;
    LastHubToDestinationRes lastHubToDestination;


    public static GetShipmentRoutesResponse from(ShipmentRoute shipmentRoute) {

        assert (!shipmentRoute.getInterhubRoutes().isEmpty());

        List<ShipmentInterhubRouteRes> interhubRouteResList
                = shipmentRoute.getInterhubRoutes().stream()
                .map(ShipmentInterhubRouteRes::from).toList();

        LastHubToDestinationRes finalHubToDestination
                = LastHubToDestinationRes.from(shipmentRoute.getLastHubToDestination());

        return GetShipmentRoutesResponse.of(interhubRouteResList, finalHubToDestination);
    }
}
