package com.sparta_express.auth.user.service;

import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.common.auditing.AuditorContext;
import com.sparta_express.auth.config.AuthConfig;
import com.sparta_express.auth.jwt.RefreshTokenRepository;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.dto.UserResponseDto;
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
    public Page<UserResponseDto> getUsers(Pageable pageable, User user) {
        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(UserResponseDto::from);
    }

    @Override
    public UserResponseDto getUser(Long userId, User loginUser) {
        User user = userRepository.findById(userId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_USER));

        return UserResponseDto.from(user);
    }

    public void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        });
    }
}
