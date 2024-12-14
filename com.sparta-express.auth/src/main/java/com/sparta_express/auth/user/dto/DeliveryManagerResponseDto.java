package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.DeliveryManager;
import com.sparta_express.auth.user.entity.DeliveryType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryManagerResponseDto {

    private DeliveryType type;
    private int deliverySequence;

    @Builder
    private DeliveryManagerResponseDto(DeliveryType type, int deliverySequence) {
        this.type = type;
        this.deliverySequence = deliverySequence;
    }

    public static DeliveryManagerResponseDto of(DeliveryManager deliveryManager) {
        return builder()
            .type(deliveryManager.getType())
            .deliverySequence(deliveryManager.getDeliverySequence())
            .build();
    }
}
