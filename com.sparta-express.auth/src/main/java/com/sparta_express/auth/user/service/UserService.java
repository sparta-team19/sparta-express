package com.sparta_express.auth.user.service;

import com.querydsl.core.types.Predicate;
import com.sparta_express.auth.user.dto.DeliveryManagerResponseDto;
import com.sparta_express.auth.user.dto.SignUpRequestDto;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.dto.UserResponseDto;
import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.entity.UserRole;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void signup(SignUpRequestDto requestDto);

    void checkDuplicateEmail(String email);

    Page<UserResponseDto> getUsers(Pageable pageable, User user);

    UserResponseDto getUser(Long userId, User user);

    UserResponseDto updateUser(Long userId, User user, UserRequestDto requestDto);

    Void deleteUser(Long userId, User user);

    Page<UserResponseDto> serchUser(String userId, UserRole role, Predicate predicate, Pageable pageable);

    DeliveryManagerResponseDto createDeliveryManager(String userId, UserRequestDto requestDto);

    Page<DeliveryManagerResponseDto> getDeliveryManagers(String userId, Pageable pageable);

    DeliveryManagerResponseDto getDeliveryManager(UUID deliveryId);

    DeliveryManagerResponseDto updateDeliveryManager(UUID deliveryId, UserRequestDto requestDto);

    void deleteDeliveryManager(UUID deliveryId);

    Page<DeliveryManagerResponseDto> searchDeliveryManager(Predicate predicate, Pageable pageable);
}
