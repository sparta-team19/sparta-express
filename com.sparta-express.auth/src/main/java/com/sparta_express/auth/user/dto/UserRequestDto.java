package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @Size(min = 4, max = 10, message = "유저이름은 4자 이상, 10자 이하로 입력해야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "유저이름은 알파벳 소문자와 숫자로 구성해야 합니다.")
    private String username;

    @NotBlank
    private String email;

    @Size(min = 8, max = 15, message = "패스워드는 8자 이상, 15자 이하로 입력해야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "패스워드는 알파벳 대소문자, 숫자, 특수문자로 구성해야 합니다.")
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String slackId;

    @NotBlank
    private UserRole role;
}
