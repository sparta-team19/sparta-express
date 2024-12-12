package com.sparta_express.hub.presentation.controller;


import com.sparta_express.hub.application.InterhubRoutesService;
import com.sparta_express.hub.domain.model.ShipmentRoutes;
import com.sparta_express.hub.presentation.dto.GetShipmentRoutesResponse;
import com.sparta_express.hub.presentation.response.ResponseDataDto;
import com.sparta_express.hub.presentation.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/interhub-routes")
@RequiredArgsConstructor
public class InterhubRoutesController {

    private final InterhubRoutesService interhubRoutesService;

    public ResponseEntity<ResponseDataDto<GetShipmentRoutesResponse>>
    getShipmentRoutes(@RequestParam UUID originHubId,
                      @RequestParam String destinationAddress) {

        ShipmentRoutes shipmentRoutes
                = interhubRoutesService.findShipmentRoutes(originHubId, destinationAddress);

        return ResponseEntity.ok(
                new ResponseDataDto<>(
                        ResponseStatus.GET_SHIPMENT_ROUTES_SUCCESS,
                        GetShipmentRoutesResponse.from(shipmentRoutes)
                )
        );
    }
}
