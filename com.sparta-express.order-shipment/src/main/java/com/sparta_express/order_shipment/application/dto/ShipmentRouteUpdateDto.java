package com.sparta_express.order_shipment.application.dto;

import com.sparta_express.order_shipment.domain.entity.ShipmentStatusEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentRouteUpdateDto {
    private Integer sequence;
    private UUID originHubId;
    private UUID destinationHubId;
    private String deliveryAddress;
    private ShipmentStatusEnum status;
    private Integer estimatedDistance;
    private Integer estimatedTime;
    private Integer actualDistance;
    private Integer actualTime;
    private UUID deliveryManagerId;

    @Builder
    private ShipmentRouteUpdateDto(Integer sequence, UUID originHubId,
                                  UUID destinationHubId, String deliveryAddress,
                                  ShipmentStatusEnum status, Integer estimatedDistance,
                                  Integer estimatedTime, Integer actualDistance,
                                  Integer actualTime, UUID deliveryManagerId) {
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
    }

    public static ShipmentRouteUpdateDto of(Integer sequence, UUID originHubId,
                                            UUID destinationHubId, String deliveryAddress,
                                            ShipmentStatusEnum status, Integer estimatedDistance,
                                            Integer estimatedTime, Integer actualDistance,
                                            Integer actualTime, UUID deliveryManagerId) {
        return ShipmentRouteUpdateDto.builder()
                .sequence(sequence)
                .originHubId(originHubId)
                .destinationHubId(destinationHubId)
                .deliveryAddress(deliveryAddress)
                .status(status)
                .estimatedDistance(estimatedDistance)
                .estimatedTime(estimatedTime)
                .actualDistance(actualDistance)
                .actualTime(actualTime)
                .deliveryManagerId(deliveryManagerId)
                .build();
    }

}
