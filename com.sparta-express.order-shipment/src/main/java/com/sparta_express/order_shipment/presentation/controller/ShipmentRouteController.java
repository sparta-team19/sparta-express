package com.sparta_express.order_shipment.presentation.controller;

import com.querydsl.core.types.Predicate;
import com.sparta_express.order_shipment.application.dto.*;
import com.sparta_express.order_shipment.application.service.ShipmentRouteService;
import com.sparta_express.order_shipment.common.util.PageableUtil;
import com.sparta_express.order_shipment.domain.entity.ShipmentRoute;
import com.sparta_express.order_shipment.presentation.dto.ShipmentRouteUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.sparta_express.order_shipment.application.dto.ResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipmentRoutes")
public class ShipmentRouteController {

    private final ShipmentRouteService shipmentRouteService;

    @PutMapping("/{shipmentRoutesId}")
    public ResponseEntity<ResponseDataDto<ShipmentRouteResponse>> updateShipmentRoute(@PathVariable("shipmentRoutesId") UUID shipmentRoutesId,
                                                                                      @RequestBody ShipmentRouteUpdateRequest request,
                                                                                      @RequestHeader(name = "X-Email") String email) {
        ShipmentRouteResponse responseDto = shipmentRouteService.updateShipmentRoute(shipmentRoutesId, request.toDto(), email);
        return ResponseEntity.ok(new ResponseDataDto<ShipmentRouteResponse>(SHIPMENT_ROUTE_UPDATE_SUCCESS, responseDto));
    }

    @GetMapping
    public ResponseEntity<ResponseDataDto<Page<ShipmentRouteResponse>>> getShipmentRoutesList(
            @QuerydslPredicate(root = ShipmentRoute.class) Predicate predicate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc) {
        Pageable pageable = PageableUtil.createPageableWithSorting(page, size, sortBy, isAsc);
        Page<ShipmentRouteResponse> responseDtoPage = shipmentRouteService.getShipmentRoutesList(predicate, pageable);
        return ResponseEntity.ok(new ResponseDataDto<Page<ShipmentRouteResponse>>(SHIPMENT_ROUTE_GET_SUCCESS, responseDtoPage));
    }

    @GetMapping("/{shipmentRoutesId}")
    public ResponseEntity<ResponseDataDto<ShipmentRouteResponse>> getShipmentRoute(@PathVariable("shipmentRoutesId") UUID shipmentRoutesId) {
        ShipmentRouteResponse responseDto = shipmentRouteService.getShipmentRoute(shipmentRoutesId);
        return ResponseEntity.ok(new ResponseDataDto<>(SHIPMENT_ROUTE_GET_SUCCESS, responseDto));
    }

    @DeleteMapping("/{shipmentRoutesId}")
    public ResponseEntity<ResponseMessageDto> deleteShipmentRoute(@PathVariable("shipmentRoutesId") UUID shipmentRoutesId,
                                                                  @RequestHeader(name = "X-Email") String userId) {
        shipmentRouteService.deleteShipmentRoute(shipmentRoutesId, userId);
        return ResponseEntity.ok(new ResponseMessageDto(SHIPMENT_ROUTE_DELETE_SUCCESS));
    }

}
