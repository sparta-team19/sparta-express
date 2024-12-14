package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.DeliveryManager;
import com.sparta_express.auth.user.entity.DeliveryType;
import com.sparta_express.auth.user.entity.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryManagerResponseDto {

    private String username;
    private String email;
    private String password;
    private String nickname;
    private String slackId;
    private UserRole role;
    private Boolean isPublic;
    private DeliveryType type;
    private int deliverySequence;

    @Builder
    private DeliveryManagerResponseDto(String username, String email, String password, String nickname, String slackId, UserRole role, Boolean isPublic, DeliveryType type, int deliverySequence) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.slackId = slackId;
        this.role = role;
        this.isPublic = isPublic;
        this.type = type;
        this.deliverySequence = deliverySequence;
    }

    public static DeliveryManagerResponseDto of(DeliveryManager deliveryManager) {
        return builder()
            .username(deliveryManager.getUser().getUsername())
            .email(deliveryManager.getUser().getEmail())
            .password(deliveryManager.getUser().getPassword())
            .nickname(deliveryManager.getUser().getNickname())
            .slackId(deliveryManager.getUser().getSlackId())
            .role(deliveryManager.getUser().getRole())
            .isPublic(deliveryManager.getUser().getIsPublic())
            .type(deliveryManager.getType())
            .deliverySequence(deliveryManager.getDeliverySequence())
            .build();
    }
}
