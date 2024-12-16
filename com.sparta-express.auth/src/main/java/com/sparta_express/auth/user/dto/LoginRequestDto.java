package com.sparta_express.auth.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String email;
    private String password;

}
