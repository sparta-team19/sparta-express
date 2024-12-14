package com.sparta_express.auth.user.service;

import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.common.auditing.AuditorContext;
import com.sparta_express.auth.config.AuthConfig;
import com.sparta_express.auth.jwt.RefreshTokenRepository;
import com.sparta_express.auth.user.dto.DeliveryManagerResponseDto;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.dto.UserResponseDto;
import com.sparta_express.auth.user.entity.DeliveryManager;
import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.repository.DeliveryManagerRepository;
import com.sparta_express.auth.user.repository.UserRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
    public void signup(UserRequestDto requestDto) {
        String encodedPassword = authConfig.passwordEncoder().encode(requestDto.getPassword());
        User user = User.of(requestDto, encodedPassword);

        checkDuplicateEmail(requestDto.getEmail());

        AuditorContext.setCurrentEmail(requestDto.getEmail());

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<?> getUsers(Pageable pageable, User user) {
        // 이거 로직을 수정해야할듯 ( 유저가 로그인유저 기준임)
        // TODO: 질문 - 사용자별로 딱 맞는 Dto를 쓰는게 맞는지 (검증 로직을 계속 돌려야함), 그냥 DeliveryManagerDto로 써서 일반유저는 null값을 두는게 맞는 지.. 아니지 없자나?
        if (isDeliveryManager(user)) {

            Page<DeliveryManager> deliveryManagersPage = deliveryManagerRepository.findAllByUserId(
                user.getId(), pageable);

            return deliveryManagersPage.map(DeliveryManagerResponseDto::of);

        } else {
            // 일반 사용자에 대한 처리
            Page<User> usersPage = userRepository.findAll(pageable); // User 목록을 페이지로 가져오기

            return usersPage.map(UserResponseDto::of);
        }
    }

    private Boolean isDeliveryManager(User user) {
        return deliveryManagerRepository.findByUserId(user.getId())
            .map(deliveryManager -> true)
            .orElse(false);
    }

    public void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        });
    }
}
