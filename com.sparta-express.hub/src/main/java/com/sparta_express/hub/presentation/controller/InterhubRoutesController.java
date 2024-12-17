package com.sparta_express.hub.presentation.controller;


import com.sparta_express.hub.application.ShipmentRouteService;
import com.sparta_express.hub.domain.model.ShipmentRoute;
import com.sparta_express.hub.presentation.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/interhub-routes")
@RequiredArgsConstructor
public class InterhubRoutesController {

    private final ShipmentRouteService shipmentRouteService;

    @GetMapping("/shipment-routes")
    public ResponseEntity<ResponseDataDto<GetShipmentRoutesResponse>>
    getShipmentRoutes(@RequestParam UUID originHubId,
                      @RequestParam String destinationAddress) {

        ShipmentRoute shipmentRoute
                = shipmentRouteService.findShipmentRoutes(originHubId, destinationAddress);

        return ResponseEntity.ok(
                new ResponseDataDto<>(
                        ResponseStatus.GET_SHIPMENT_ROUTES_SUCCESS,
                        GetShipmentRoutesResponse.from(shipmentRoute)
                )
        );
    }

    @GetMapping("/shipment-interhub-routes")
    public ResponseEntity<ResponseDataDto<List<InterhubRouteResponse>>>
    getShipmentInterhubRoutes(@RequestParam UUID originHubId,
                              @RequestParam UUID destinationHubId) {

        List<InterhubRouteResponse> interhubRoutes
                = shipmentRouteService.findShipmentInterhubRoutes(originHubId, destinationHubId)
                .stream().map(InterhubRouteResponse::from).toList();

        return ResponseEntity.ok(
                new ResponseDataDto<>(
                        ResponseStatus.GET_SHIPMENT_ROUTES_SUCCESS,
                        interhubRoutes
                )
        );
    }

}
