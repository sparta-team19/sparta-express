package com.sparta_express.order_shipment.presentation.controller;

import com.querydsl.core.types.Predicate;
import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.application.dto.ResponseMessageDto;
import com.sparta_express.order_shipment.application.dto.ShipmentResponse;
import com.sparta_express.order_shipment.application.service.ShipmentService;
import com.sparta_express.order_shipment.common.util.PageableUtil;
import com.sparta_express.order_shipment.domain.entity.Shipment;
import com.sparta_express.order_shipment.presentation.dto.ShipmentUpdateRequest;
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
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PutMapping("/{shipmentId}")
    public ResponseEntity<ResponseDataDto<ShipmentResponse>> updateShipment(@PathVariable("shipmentId") UUID shipmentId,
                                                                            @RequestBody ShipmentUpdateRequest request,
                                                                            @RequestHeader(value = "X-User-Id") String userId) {
        ShipmentResponse responseDto = shipmentService.updateShipment(shipmentId, request.toDto(), userId);
        return ResponseEntity.ok(new ResponseDataDto<>(SHIPMENT_UPDATE_SUCCESS, responseDto));
    }

    @GetMapping
    public ResponseEntity<ResponseDataDto<Page<ShipmentResponse>>> getShipmentsList(@QuerydslPredicate(root = Shipment.class) Predicate predicate,
                                                                                    @RequestParam(defaultValue = "1") int page,
                                                                                    @RequestParam(defaultValue = "10") int size,
                                                                                    @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                                    @RequestParam(defaultValue = "false") boolean isAsc) {
        Pageable pageable = PageableUtil.createPageableWithSorting(page, size, sortBy, isAsc);
        Page<ShipmentResponse> ResponseDtoPage = shipmentService.getShipmentsList(predicate, pageable);
        return ResponseEntity.ok(new ResponseDataDto<>(SHIPMENT_GET_SUCCESS, ResponseDtoPage));
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ResponseDataDto<ShipmentResponse>> getShipment(@PathVariable("shipmentId") UUID shipmentId) {
        ShipmentResponse responseDto = shipmentService.getShipment(shipmentId);
        return ResponseEntity.ok(new ResponseDataDto<>(SHIPMENT_GET_SUCCESS, responseDto));
    }

    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<ResponseMessageDto> deleteShipment(@PathVariable("shipmentId") UUID shipmentId,
                                                             @RequestHeader(value = "X-User-Id") String userId) {
        shipmentService.deleteShipment(shipmentId, userId);
        return ResponseEntity.ok(new ResponseMessageDto(SHIPMENT_DELETE_SUCCESS));
    }

}
