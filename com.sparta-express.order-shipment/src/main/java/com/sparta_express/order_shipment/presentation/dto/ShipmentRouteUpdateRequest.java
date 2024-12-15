package com.sparta_express.order_shipment.presentation.dto;

import com.sparta_express.order_shipment.application.dto.ShipmentRouteUpdateDto;
import com.sparta_express.order_shipment.domain.entity.ShipmentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentRouteUpdateRequest {
    private Integer sequence;
    private UUID originHubId;
    private UUID destinationHubId;
    private String deliveryAddress;
    private ShipmentStatusEnum status;
    private Float estimatedDistance;
    private Integer estimatedTime;
    private Float actualDistance;
    private Integer actualTime;
    private Long deliveryManagerId;

    public ShipmentRouteUpdateDto toDto() {
        return ShipmentRouteUpdateDto.of(
                this.sequence, this.originHubId,
                this.destinationHubId, this.deliveryAddress,
                this.status, this.estimatedDistance,
                this.estimatedTime, this.actualDistance,
                this.actualTime, this.deliveryManagerId
        );
    }

}
