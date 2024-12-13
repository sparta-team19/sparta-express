package com.sparta_express.auth.user.service;

import com.sparta_express.auth.user.dto.LoginResponseDto;
import com.sparta_express.auth.user.dto.UserRequestDto;

public interface UserService {

    void signup(UserRequestDto requestDto);

    void checkDuplicateEmail(String email);

//    String createToken(LoginResponseDto loginResponseDto);
}
