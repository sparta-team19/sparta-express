package com.sparta_express.auth.user.service;

import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void signup(UserRequestDto requestDto);

    void checkDuplicateEmail(String email);

    Page<?> getUsers(Pageable pageable, User user);
}
