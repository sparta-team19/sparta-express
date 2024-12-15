package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.DeliveryType;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private String username;
    private String email;
    private String nickname;
    private String slackId;
    private Boolean isPublic;
    private DeliveryType type;
    private int deliverySequence;
}
