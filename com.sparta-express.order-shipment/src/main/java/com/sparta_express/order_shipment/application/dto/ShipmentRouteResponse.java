package com.sparta_express.order_shipment.application.dto;

import com.sparta_express.order_shipment.domain.entity.ShipmentRoute;
import com.sparta_express.order_shipment.domain.entity.ShipmentStatusEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentRouteResponse {
    private UUID orderId;
    private UUID shipmentId;
    private UUID shipmentRouteId;
    private Integer sequence;
    private UUID originHubId;
    private UUID destinationHubId;
    private String deliveryAddress;
    private ShipmentStatusEnum status;
    private Float estimatedDistance;
    private Integer estimatedTime;
    private Float actualDistance;
    private Integer actualTime;
    private UUID deliveryManagerId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isDelete;

    @Builder
    private ShipmentRouteResponse(UUID orderId, UUID shipmentId, UUID shipmentRouteId,
                                  Integer sequence, UUID originHubId, UUID destinationHubId,
                                  String deliveryAddress, ShipmentStatusEnum status,
                                  Float estimatedDistance, Integer estimatedTime,
                                  Float actualDistance, Integer actualTime,
                                  UUID deliveryManagerId, String userId, LocalDateTime createdAt,
                                  LocalDateTime updatedAt, String createdBy,
                                  String updatedBy, Boolean isDelete) {
        this.orderId = orderId;
        this.shipmentId = shipmentId;
        this.shipmentRouteId = shipmentRouteId;
        this.sequence = sequence;
        this.originHubId = originHubId;
        this.destinationHubId = destinationHubId;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.actualDistance = actualDistance;
        this.actualTime = actualTime;
        this.deliveryManagerId = deliveryManagerId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDelete = isDelete;
    }

    public static ShipmentRouteResponse from(ShipmentRoute shipmentRoute) {
        return ShipmentRouteResponse.builder()
                .orderId(shipmentRoute.getShipment().getOrder().getId())
                .shipmentId(shipmentRoute.getShipment().getId())
                .shipmentRouteId(shipmentRoute.getId())
                .sequence(shipmentRoute.getSequence())
                .originHubId(shipmentRoute.getOriginHubId())
                .destinationHubId(shipmentRoute.getDestinationHubId())
                .deliveryAddress(shipmentRoute.getDeliveryAddress())
                .status(shipmentRoute.getStatus())
                .estimatedDistance(shipmentRoute.getEstimatedDistance())
                .estimatedTime(shipmentRoute.getEstimatedTime())
                .actualDistance(shipmentRoute.getActualDistance())
                .actualTime(shipmentRoute.getActualTime())
                .deliveryManagerId(shipmentRoute.getDeliveryManagerId())
                .userId(shipmentRoute.getUserId())
                .createdAt(shipmentRoute.getCreatedAt())
                .updatedAt(shipmentRoute.getUpdatedAt())
                .createdBy(shipmentRoute.getCreatedBy())
                .updatedBy(shipmentRoute.getUpdatedBy())
                .isDelete(shipmentRoute.getIsDelete())
                .build();
    }

}
