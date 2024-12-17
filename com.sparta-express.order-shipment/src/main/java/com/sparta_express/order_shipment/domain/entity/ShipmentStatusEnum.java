package com.sparta_express.order_shipment.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShipmentStatusEnum {

    HUB_WAITING("HUB_WAITING"),
    HUB_IN_TRANSIT("HUB_IN_TRANSIT"),
    HUB_ARRIVED("HUB_ARRIVED"),
    DELIVERING("DELIVERING"),
    COMPLETED("COMPLETED");

    private final String ShipmentStatus;

    @JsonCreator
    public static ShipmentStatusEnum from(String value) {
        for (ShipmentStatusEnum status : ShipmentStatusEnum.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }

}
