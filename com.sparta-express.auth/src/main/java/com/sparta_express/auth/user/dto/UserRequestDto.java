package com.sparta_express.auth.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String username;
    private String email;
    private String nickname;
    private String slackId;
    private Boolean isPublic;
}
