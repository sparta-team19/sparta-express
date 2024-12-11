package com.sparta_express.auth.service;

import com.sparta_express.auth.AuthConfig;
import com.sparta_express.auth.entity.User;
import com.sparta_express.auth.dtos.UserRequestDto;
import com.sparta_express.auth.repository.UserRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final AuthConfig authConfig;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;

    public AuthService(UserRepository userRepository, AuthConfig authConfig, @Value("${service.jwt.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.authConfig = authConfig;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    @Transactional
    public void signup(UserRequestDto requestDto) {
        String encodedPassword = authConfig.passwordEncoder().encode(requestDto.getPassword());
        User user = User.of(requestDto, encodedPassword);
        userRepository.save(user);
    }
}
