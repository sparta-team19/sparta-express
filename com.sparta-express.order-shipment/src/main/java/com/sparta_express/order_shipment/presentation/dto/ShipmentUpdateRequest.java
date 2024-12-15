package com.sparta_express.order_shipment.presentation.dto;

import com.sparta_express.order_shipment.application.dto.ShipmentUpdateDto;
import com.sparta_express.order_shipment.domain.entity.ShipmentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentUpdateRequest {
    private UUID originHubId;
    private UUID destinationHubId;
    private String deliveryAddress;
    private String recipientName;
    private String recipientSlackId;
    private ShipmentStatusEnum status;

    public ShipmentUpdateDto toDto() {
        return ShipmentUpdateDto.of(
                this.originHubId,
                this.destinationHubId,
                this.deliveryAddress,
                this.recipientName,
                this.recipientSlackId,
                this.status
        );
    }

}
