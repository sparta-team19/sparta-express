package com.sparta_express.auth.user.service;

import com.querydsl.core.types.Predicate;
import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.common.auditing.AuditorContext;
import com.sparta_express.auth.config.AuthConfig;
import com.sparta_express.auth.jwt.RefreshTokenRepository;
import com.sparta_express.auth.user.dto.DeliveryManagerResponseDto;
import com.sparta_express.auth.user.dto.SignUpRequestDto;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.dto.UserResponseDto;
import com.sparta_express.auth.user.entity.DeliveryManager;
import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.entity.UserRole;
import com.sparta_express.auth.user.repository.DeliveryManagerRepository;
import com.sparta_express.auth.user.repository.UserRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthConfig authConfig;
    private final SecretKey secretKey;
    private final PasswordEncoder passwordEncoder;
    private final DeliveryManagerRepository deliveryManagerRepository;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;


    public UserServiceImpl(UserRepository userRepository, AuthConfig authConfig,
        @Value("${service.jwt.secret-key}") String secretKey,
        PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository,
        DeliveryManagerRepository deliveryManagerRepository) {
        this.userRepository = userRepository;
        this.authConfig = authConfig;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.deliveryManagerRepository = deliveryManagerRepository;
    }

    @Transactional
    public void signup(SignUpRequestDto requestDto) {
        String encodedPassword = authConfig.passwordEncoder().encode(requestDto.getPassword());
        User user = User.of(requestDto, encodedPassword);

        checkDuplicateEmail(requestDto.getEmail());

        AuditorContext.setCurrentEmail(requestDto.getEmail());

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponseDto> getUsers(Pageable pageable, User user) {
        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(UserResponseDto::from);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public UserResponseDto getUser(Long userId, User loginUser) {
//        User user = userRepository.findById(userId).orElseThrow(() ->
//            new CustomException(ErrorType.NOT_FOUND_USER));
//
//        return UserResponseDto.from(user);
//    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmailAndIsDeleted(email, false).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_USER));

        return UserResponseDto.from(user);
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(Long userId, User loginUser, UserRequestDto requestDto) {
        validateUserModification(userId, loginUser);

        User user = userRepository.findById(userId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_USER));

        user.updateUser(requestDto);

        return UserResponseDto.from(user);
    }

    @Transactional
    @Override
    public Void deleteUser(Long userId, User loginUser) {
        if (isLoginUserOrManager(userId, loginUser)) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_USER));

            user.setIsDeleted(Boolean.TRUE);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponseDto> serchUser(String userId, UserRole role, Predicate predicate,
        Pageable pageable) {
        if (role != UserRole.MASTER) {
            new CustomException(ErrorType.ACCESS_DENIED);
        }
        Page<User> usersPage = userRepository.findAll(predicate, pageable);
        return usersPage.map(UserResponseDto::from);
    }

    @Transactional
    @Override
    public DeliveryManagerResponseDto createDeliveryManager(String userId,
        UserRequestDto requestDto) {

        // 현재 최대 deliverySequence 값 조회
        Integer maxSequence = deliveryManagerRepository.findMaxDeliverySequence();

        // 새로운 deliverySequence 설정
        int newDeliverySequence = (maxSequence == null ? 1 : maxSequence + 1);

        DeliveryManager deliveryManager = DeliveryManager.of(requestDto, newDeliverySequence);

        deliveryManagerRepository.save(deliveryManager);

        return DeliveryManagerResponseDto.from(deliveryManager);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeliveryManagerResponseDto> getDeliveryManagers(String userId, Pageable pageable) {
        Page<DeliveryManager> deliveryManagerPage = deliveryManagerRepository.findAll(pageable);
        return deliveryManagerPage.map(DeliveryManagerResponseDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public DeliveryManagerResponseDto getDeliveryManager(UUID deliveryId) {
        DeliveryManager deliveryManager = deliveryManagerRepository.findById(deliveryId)
            .orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_DELIVERY_MANAGER));
        return DeliveryManagerResponseDto.from(deliveryManager);
    }

    @Transactional
    @Override
    public DeliveryManagerResponseDto updateDeliveryManager(UUID deliveryId,
        UserRequestDto requestDto) {
        DeliveryManager deliveryManager = deliveryManagerRepository.findById(deliveryId)
            .orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_DELIVERY_MANAGER));
        deliveryManager.updateDeliveryManager(requestDto);
        return DeliveryManagerResponseDto.from(deliveryManager);
    }

    @Transactional
    @Override
    public void deleteDeliveryManager(UUID deliveryId) {
        DeliveryManager deliveryManager = deliveryManagerRepository.findById(deliveryId)
            .orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_DELIVERY_MANAGER));

        deliveryManager.setIsDeleted(Boolean.TRUE);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeliveryManagerResponseDto> searchDeliveryManager(Predicate predicate,
        Pageable pageable) {
        Page<DeliveryManager> deliveryManagerPage = deliveryManagerRepository.findAll(predicate, pageable);
        return deliveryManagerPage.map(DeliveryManagerResponseDto::from);
    }

    private boolean isLoginUserOrManager(Long userId, User loginUser) {
        if (userId.equals(loginUser.getId()) || UserRole.MASTER == loginUser.getRole()) {
            return true;
        } else {
            new CustomException(ErrorType.UNAUTHORIZED_ACCESS);
        }
        return false;
    }

    // 본인 확인
    private void validateUserModification(Long userId, User loginUser) {
        if (!userId.equals(loginUser.getId())) {
            new CustomException(ErrorType.UNAUTHORIZED_ACCESS);
        }
    }

    // 이메일 중복 검사
    public void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        });
    }
}
