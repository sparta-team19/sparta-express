package com.sparta_express.hub.presentation.dto;

import com.sparta_express.hub.application.dto.FinalHubToDestination;
import com.sparta_express.hub.domain.model.ShipmentRoutes;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class GetShipmentRoutesResponse {

    @NonNull List<GetShipmentRoutesResInterhubRoute> interhubRoutes;
    @NonNull FinalHubToDestination finalHubToDestination;


    public static GetShipmentRoutesResponse from(ShipmentRoutes shipmentRoutes) {

        return GetShipmentRoutesResponse.builder()
                .interhubRoutes(
                        shipmentRoutes.getInterhubRoutes()
                                .stream()
                                .map(GetShipmentRoutesResInterhubRoute::from)
                                .toList()
                )
                .finalHubToDestination(shipmentRoutes.getFinalHubToDestination())
                .build();
    }
}
