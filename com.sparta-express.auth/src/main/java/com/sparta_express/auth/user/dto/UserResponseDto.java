package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.entity.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {

    private String username;
    private String email;
    private String password;
    private String nickname;
    private String slackId;
    private UserRole role;
    private Boolean isPublic;

    @Builder
    private UserResponseDto(String username, String email, String password, String nickname, String slackId, UserRole role, Boolean isPublic) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.slackId = slackId;
        this.role = role;
        this.isPublic = isPublic;
    }

    public static UserResponseDto of(User user) {
        return builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .nickname(user.getNickname())
            .slackId(user.getSlackId())
            .role(user.getRole())
            .isPublic(user.getIsPublic())
            .build();
    }

}
