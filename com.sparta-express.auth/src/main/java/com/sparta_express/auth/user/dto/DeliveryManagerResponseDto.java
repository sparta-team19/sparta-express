package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.DeliveryManager;
import com.sparta_express.auth.user.entity.DeliveryType;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryManagerResponseDto {

    private UUID deliveryManagerId;
    private DeliveryType type;
    private int deliverySequence;

    @Builder
    private DeliveryManagerResponseDto(UUID deliveryManagerId, DeliveryType type, int deliverySequence) {
        this.deliveryManagerId = deliveryManagerId;
        this.type = type;
        this.deliverySequence = deliverySequence;
    }

    public static DeliveryManagerResponseDto from(DeliveryManager deliveryManager) {
        return builder()
            .deliveryManagerId(deliveryManager.getId())
            .type(deliveryManager.getType())
            .deliverySequence(deliveryManager.getDeliverySequence())
            .build();
    }
}
