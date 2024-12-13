package com.sparta_express.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.jwt.JwtTokenProvider;
import com.sparta_express.auth.jwt.JwtTokenValidator;
import com.sparta_express.auth.jwt.RefreshToken;
import com.sparta_express.auth.jwt.RefreshTokenRepository;
import com.sparta_express.auth.user.entity.User;
import com.sparta_express.auth.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.CookieGenerator;

@Slf4j(topic = "JWT 검증 및 인가")
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenValidator jwtTokenValidator; // JWT 검증 서비스

    @Autowired
    private UserDetailsService userDetailsService; // 사용자 세부 정보 서비스

    @Autowired
    private UserRepository userRepository; // user 리포지토리

    @Autowired
    private RefreshTokenRepository refreshTokenRepository; // Refresh Token 리포지토리

    private final String AUTH_HEADER = "Authorization"; // 헤더 이름
    private final String COOKIE_NAME = "token"; // 쿠키 이름

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTH_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 부분 제거

            // Access Token 검증
            User found;

            try {
                // Access Token 유효성 검증
                jwtTokenValidator.validateToken(token, response); // 유효성 검증 (예외 발생 시 아래로 넘어감)

                // 사용자 정보 로드
                found = userRepository.findByEmail(
                    JwtTokenProvider.getEmailFromToken(token)).orElseThrow(() ->
                    new CustomException(ErrorType.NOT_FOUND_EMAIL));

            } catch (ExpiredJwtException e) {
                log.error("Access Token이 만료되었습니다.");

                // 만료된 Access Token으로 Redis에서 Refresh Token 정보 조회
                RefreshToken foundTokenInfo = refreshTokenRepository.findByAccessToken(token)
                    .orElseThrow(() -> new CustomException(ErrorType.TOKEN_NOT_FOUND));

                String refreshToken = foundTokenInfo.getRefreshToken();

                // 만약 refresh 토큰도 만료되었다면, ExceptionHandlerFilter에서 처리된다.
                JwtTokenProvider.isExpired(refreshToken);

                // refresh 토큰이 아직 유효하다면, redis에 함께 저장해둔, userId를 가져온다.
                Long userId = Long.valueOf(foundTokenInfo.getId());

                found = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

                // 새로운 Access Token 생성
                token = JwtTokenProvider.createAccessToken(found.getEmail(), found.getRole());

                // Redis에 새로운 Access Token 저장
                refreshTokenRepository.save(
                    new RefreshToken(String.valueOf(userId), refreshToken, token));

                // 클라이언트 측 쿠키에 새로운 Access Token 업데이트
                CookieGenerator cookieGenerator = new CookieGenerator();
                cookieGenerator.setCookieName(COOKIE_NAME);
                cookieGenerator.setCookieHttpOnly(true);
                cookieGenerator.addCookie(response, token);
                cookieGenerator.setCookieMaxAge(60 * 60); // 1시간
            }
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String accessToken = jwtTokenValidator.getJwtAccessTokenFromHeader(request);

        if (StringUtils.hasText(accessToken) && jwtTokenValidator.validateToken(accessToken,
            response)) {

            Claims claims = JwtTokenProvider.getUserInfoFromToken(accessToken);

            if (claims.get(JwtTokenProvider.REFRESH_HEADER) != null) {
                putRefreshTokenInAuthorization(response);
            }

            setAuthentication(claims.getSubject());

        }

        filterChain.doFilter(request, response);
    }

    private void putRefreshTokenInAuthorization(HttpServletResponse response) throws IOException {
        CustomException errorResponse = new CustomException(ErrorType.NOT_ACCESS_TOKEN);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);

    }
}