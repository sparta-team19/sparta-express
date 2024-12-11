package com.sparta_express.auth.dtos;

import com.sparta_express.auth.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String username;
    private String email;
    private String password;
    private String nickname;
    private String slackId;
    private UserRole role;
}
