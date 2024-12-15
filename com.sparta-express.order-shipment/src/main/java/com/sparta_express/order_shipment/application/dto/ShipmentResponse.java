package com.sparta_express.order_shipment.application.dto;

import com.sparta_express.order_shipment.domain.entity.Shipment;
import com.sparta_express.order_shipment.domain.entity.ShipmentRoute;
import com.sparta_express.order_shipment.domain.entity.ShipmentStatusEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentResponse {
    private UUID orderId;
    private UUID shipmentId;
    private UUID originHubId;
    private UUID destinationHubId;
    private String deliveryAddress;
    private String recipientName;
    private String recipientSlackId;
    private ShipmentStatusEnum status;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isDelete;
    private List<ShipmentRouteDto> shipmentRoutes;

    @Builder
    private ShipmentResponse(UUID orderId, UUID shipmentId, UUID originHubId,
                             UUID destinationHubId, String deliveryAddress,
                             String recipientName, String recipientSlackId,
                             ShipmentStatusEnum status, LocalDateTime createdAt,
                             LocalDateTime updatedAt, String createdBy, String updatedBy,
                             Boolean isDelete, String userId, List<ShipmentRouteDto> shipmentRoutes) {
        this.orderId = orderId;
        this.shipmentId = shipmentId;
        this.originHubId = originHubId;
        this.destinationHubId = destinationHubId;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
        this.recipientSlackId = recipientSlackId;
        this.status = status;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDelete = isDelete;
        this.shipmentRoutes = shipmentRoutes;
    }

    public static ShipmentResponse from(Shipment shipment) {
        return ShipmentResponse.builder()
                .orderId(shipment.getOrder().getId())
                .shipmentId(shipment.getId())
                .originHubId(shipment.getOriginHubId())
                .destinationHubId(shipment.getDestinationHubId())
                .deliveryAddress(shipment.getDeliveryAddress())
                .recipientName(shipment.getRecipientName())
                .recipientSlackId(shipment.getRecipientSlackId())
                .status(shipment.getStatus())
                .userId(shipment.getUserId())
                .createdAt(shipment.getCreatedAt())
                .updatedAt(shipment.getUpdatedAt())
                .createdBy(shipment.getCreatedBy())
                .updatedBy(shipment.getUpdatedBy())
                .isDelete(shipment.getIsDelete())
                .shipmentRoutes(shipment.getShipmentRoutes().stream().map(ShipmentRouteDto::from).collect(Collectors.toList()))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ShipmentRouteDto {
        private UUID shipmentRouteId;
        private Integer sequence;
        private UUID originHubId;
        private UUID destinationHubId;
        private ShipmentStatusEnum status;
        private Integer estimatedDistance;
        private Integer estimatedTime;
        private Integer actualDistance;
        private Integer actualTime;
        private UUID deliveryManagerId;
        private Boolean isDelete;

        public static ShipmentRouteDto from(ShipmentRoute shipmentRoute) {
            return new ShipmentRouteDto(
                    shipmentRoute.getId(),
                    shipmentRoute.getSequence(),
                    shipmentRoute.getOriginHubId(),
                    shipmentRoute.getDestinationHubId(),
                    shipmentRoute.getStatus(),
                    shipmentRoute.getEstimatedDistance(),
                    shipmentRoute.getEstimatedTime(),
                    shipmentRoute.getActualDistance(),
                    shipmentRoute.getActualTime(),
                    shipmentRoute.getDeliveryManagerId(),
                    shipmentRoute.getIsDelete()
            );
        }

    }

}
