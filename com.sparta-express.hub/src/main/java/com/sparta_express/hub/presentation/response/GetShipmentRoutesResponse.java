package com.sparta_express.hub.presentation.response;

import com.sparta_express.hub.domain.model.ShipmentRoute;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class GetShipmentRoutesResponse {

    List<InterhubRouteResOfGetShipmentRoutes> interhubRoutes;
    FinalHubToDestinationResOfGetShipmentRoutes finalHubToDestination;


    public static GetShipmentRoutesResponse from(ShipmentRoute shipmentRoute) {

        assert (!shipmentRoute.getInterhubRoutes().isEmpty());

        List<InterhubRouteResOfGetShipmentRoutes> interhubRouteResList
                = shipmentRoute.getInterhubRoutes()
                .stream()
                .map(InterhubRouteResOfGetShipmentRoutes::from)
                .toList();
        FinalHubToDestinationResOfGetShipmentRoutes finalHubToDestination
                = FinalHubToDestinationResOfGetShipmentRoutes.from(shipmentRoute.getFinalHubToDestination());

        return GetShipmentRoutesResponse
                .builder()
                .interhubRoutes(interhubRouteResList)
                .finalHubToDestination(finalHubToDestination)
                .build();
    }
}
