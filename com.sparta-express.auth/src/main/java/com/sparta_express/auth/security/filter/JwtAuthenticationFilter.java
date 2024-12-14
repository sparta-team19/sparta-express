package com.sparta_express.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.common.ResponseMessageDto;
import com.sparta_express.auth.common.ResponseStatus;
import com.sparta_express.auth.jwt.JwtTokenProvider;
import com.sparta_express.auth.jwt.RefreshTokenService;
import com.sparta_express.auth.security.UserDetailsImpl;
import com.sparta_express.auth.user.dto.LoginRequestDto;
import com.sparta_express.auth.user.entity.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {

            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                    LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);

        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getEmail();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        long userId = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId();

        String accessToken = JwtTokenProvider.createAccessToken(username, role);
        String refreshToken = JwtTokenProvider.createRefreshToken();

        refreshTokenService.saveTokenInfo(userId, refreshToken, accessToken);

        log.info("Access_Token : {}", accessToken);
        log.info("Refresh_Token : {}", refreshToken);

        sendAccessToken(response, accessToken);

        ResponseMessageDto commonResponse = new ResponseMessageDto(ResponseStatus.LOGIN_SUCCESS);

        new ObjectMapper().writeValue(response.getOutputStream(), commonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed) throws IOException {

        //ResponseEntity 형식을 맞춰 바디에 에러 정보 작성
        CustomException errorResponse = new CustomException(ErrorType.LOGIN_FAILED);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }

    private void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.setHeader(JwtTokenProvider.ACCESS_HEADER, accessToken);
    }
}