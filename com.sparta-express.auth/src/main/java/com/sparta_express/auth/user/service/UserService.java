package com.sparta_express.auth.user.service;

import com.sparta_express.auth.user.dto.SignUpRequestDto;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.dto.UserResponseDto;
import com.sparta_express.auth.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void signup(SignUpRequestDto requestDto);

    void checkDuplicateEmail(String email);

    Page<UserResponseDto> getUsers(Pageable pageable, User user);

    UserResponseDto getUser(Long userId, User user);

    UserResponseDto updateUser(Long userId, User user, UserRequestDto requestDto);

    Void deleteUser(Long userId, User user);
}
