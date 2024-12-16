package com.sparta_express.company_product.external;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String slackId;
    private UserRole role;
    private Boolean isPublic;

}