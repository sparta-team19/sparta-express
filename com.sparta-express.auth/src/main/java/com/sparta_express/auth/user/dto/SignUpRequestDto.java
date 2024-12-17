package com.sparta_express.auth.user.dto;

import com.sparta_express.auth.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @Size(min = 4, max = 10, message = "유저이름은 4자 이상, 10자 이하로 입력해야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "유저이름은 알파벳 소문자와 숫자로 구성해야 합니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.") // 이메일 형식 검증 추가
    private String email;

    @Size(min = 8, max = 15, message = "패스워드는 8자 이상, 15자 이하로 입력해야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "패스워드는 알파벳 대소문자, 숫자, 특수문자로 구성해야 합니다.")
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String slackId;

    @NotNull
    private UserRole role;
}
