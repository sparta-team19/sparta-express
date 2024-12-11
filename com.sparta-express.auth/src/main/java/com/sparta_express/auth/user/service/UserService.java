package com.sparta_express.auth.user.service;

import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.common.auditing.AuditorContext;
import com.sparta_express.auth.config.AuthConfig;
import com.sparta_express.auth.jwt.JwtTokenProvider;
import com.sparta_express.auth.jwt.RefreshToken;
import com.sparta_express.auth.jwt.RefreshTokenRepository;
import com.sparta_express.auth.user.dto.UserResponseDto;
import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.dto.UserRequestDto;
import com.sparta_express.auth.user.repository.UserRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecretKey secretKey;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthConfig authConfig;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;


    public UserService(UserRepository userRepository, AuthConfig authConfig, @Value("${service.jwt.secret-key}") String secretKey,
        PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.authConfig = authConfig;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public void signup(UserRequestDto requestDto) {
        String encodedPassword = authConfig.passwordEncoder().encode(requestDto.getPassword());
        User user = User.of(requestDto, encodedPassword);

        checkDuplicateEmail(requestDto.getEmail());

        AuditorContext.setCurrentEmail(requestDto.getEmail());

        userRepository.save(user);
    }

    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        });
    }


    public UserResponseDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorType.INVALID_EMAIL_OR_PASSWORD));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorType.INVALID_EMAIL_OR_PASSWORD);
        }
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }

    @Transactional
    public String createToken(UserResponseDto userResponseDto) {
        //access 토큰은, 사용자 정보로 생성
        String accessToken = JwtTokenProvider.createAccessToken(userResponseDto.getEmail(), userResponseDto.getRole());
        String refreshToken = JwtTokenProvider.createRefreshToken();
        User user = userRepository.findByEmail(userResponseDto.getEmail()).get();   // 이미 위에서 검증했으니 바로 가져온다.

        //레디스에 저장 Refresh 토큰을 저장한다. (사용자 기본키 Id, refresh 토큰, access 토큰 저장)
        refreshTokenRepository.save(new RefreshToken(String.valueOf(user.getId()), refreshToken, accessToken));

        return accessToken;
    }
}
