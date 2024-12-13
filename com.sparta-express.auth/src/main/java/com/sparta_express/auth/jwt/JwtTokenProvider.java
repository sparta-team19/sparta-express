package com.sparta_express.auth.jwt;

import com.sparta_express.auth.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j(topic = "JwtTokenProvider")
@Component
public class JwtTokenProvider {

    // Header KEY 값
    public static final String ACCESS_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh-Token";

    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String REFRESH_TOKEN = "refreshToken";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${service.jwt.secret-key}") // SecretKey
    private String secretKey;

    private static Key key;

//    @Value("${service.jwt.access.expiration}")
    private long accessTokenValidityInSeconds = 1000 * 60 * 30;

//    @Value("${service.jwt.refresh.expiration}")
    private long refreshTokenValidityInSeconds = 1000 * 60 * 60 * 24 * 3;

    private static long accessTokenValidate;
    private static long refreshTokenValidate;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);

        accessTokenValidate = accessTokenValidityInSeconds * 1000;
        refreshTokenValidate = refreshTokenValidityInSeconds * 1000;
    }

    // AccessToken 토큰 생성
    public static String createAccessToken(String email, UserRole role) {

        return BEARER_PREFIX +
            Jwts.builder()
                .subject(email) // 사용자 식별자값(email)
                .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidate)) // 만료 시간
                .issuedAt(new Date(System.currentTimeMillis())) // 발급일
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘
                .compact();
    }

    // RefreshToken 토큰 생성
    public static String createRefreshToken() {

        return BEARER_PREFIX +
            Jwts.builder()
                .claim(REFRESH_HEADER, REFRESH_TOKEN)
                .expiration(new Date(System.currentTimeMillis() + refreshTokenValidate)) //유효시간 (3일)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256) //HS256알고리즘으로 key를 암호화 해줄것이다.
                .compact();
    }

    // 토큰에서 사용자 정보 가져오기
    public static Claims getUserInfoFromToken(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
