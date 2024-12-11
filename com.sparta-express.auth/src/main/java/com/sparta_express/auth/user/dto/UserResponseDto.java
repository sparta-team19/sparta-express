package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String username;
    private String email;
    private UserRole role;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
