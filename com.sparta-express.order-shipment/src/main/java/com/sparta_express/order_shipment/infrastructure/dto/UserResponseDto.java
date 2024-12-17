package com.sparta_express.order_shipment.infrastructure.dto;

import lombok.*;

import java.util.UUID;

@Getter
@ToString
public class UserResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String slackId;
    private String role;
    private Boolean isPublic;
}
