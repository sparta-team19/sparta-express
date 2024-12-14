package com.sparta_express.auth.jwt;

import com.sparta_express.auth.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
                .claim("user_id", email) // 사용자 식별자값(email)
                .claim("role", role) // 사용자 권한
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

    public static String getEmailFromToken(String token) {
        try {
            Claims claims = getUserInfoFromToken(token);
            String email = claims.getSubject(); // 이메일 가져오기

            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("이메일이 없습니다."); // 이메일이 없을 경우 예외 발생
            }

            return email;
        } catch (MalformedJwtException | SignatureException e) {
            throw new IllegalArgumentException("유효하지 않은 JWT입니다."); // JWT가 유효하지 않은 경우
        }
    }

    public static void isExpired(String token) {
        try {
            // 토큰의 Claims 가져오기
            Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
            // 만료 시간 확인
            if (claims.getExpiration().before(new Date())) {
                throw new ExpiredJwtException((Header) claims, null, "JWT 토큰이 만료되었습니다.");
            }
        } catch (ExpiredJwtException e) {
            throw e; // 만료된 토큰 예외를 그대로 던짐
        } catch (SignatureException e) {
            throw new IllegalArgumentException("JWT 서명이 유효하지 않습니다."); // 서명이 유효하지 않은 경우
        } catch (Exception e) {
            throw new IllegalArgumentException("JWT 처리 중 오류가 발생했습니다."); // 기타 예외 처리
        }
    }
}
