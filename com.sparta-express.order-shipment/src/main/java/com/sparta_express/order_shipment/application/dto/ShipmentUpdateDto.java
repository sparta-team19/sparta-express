package com.sparta_express.order_shipment.application.dto;

import com.sparta_express.order_shipment.domain.entity.ShipmentStatusEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentUpdateDto {
    private UUID originHubId;
    private UUID destinationHubId;
    private String deliveryAddress;
    private String recipientName;
    private String recipientSlackId;
    private ShipmentStatusEnum status;

    @Builder
    private ShipmentUpdateDto(UUID originHubId,
                              UUID destinationHubId,
                              String deliveryAddress,
                              String recipientName,
                              String recipientSlackId,
                              ShipmentStatusEnum status) {
        this.originHubId = originHubId;
        this.destinationHubId = destinationHubId;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
        this.recipientSlackId = recipientSlackId;
        this.status = status;
    }

    public static ShipmentUpdateDto of(UUID originHubId,
                                       UUID destinationHubId,
                                       String deliveryAddress,
                                       String recipientName,
                                       String recipientSlackId,
                                       ShipmentStatusEnum status) {
        return ShipmentUpdateDto.builder()
                .originHubId(originHubId)
                .destinationHubId(destinationHubId)
                .deliveryAddress(deliveryAddress)
                .recipientName(recipientName)
                .recipientSlackId(recipientSlackId)
                .status(status)
                .build();
    }

}
