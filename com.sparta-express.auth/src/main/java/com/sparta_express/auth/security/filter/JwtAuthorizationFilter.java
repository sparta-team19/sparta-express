//package com.sparta_express.auth.security.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta_express.auth.common.TokenErrorCode;
//import com.sparta_express.auth.jwt.JwtTokenProvider;
//import com.sparta_express.auth.jwt.JwtTokenValidator;
//import com.sparta_express.auth.jwt.RefreshTokenService;
//import com.sparta_express.auth.security.handler.UserDetailsServiceImpl;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Slf4j(topic = "JWT 검증 및 인가")
//@RequiredArgsConstructor
//public class JwtAuthorizationFilter extends OncePerRequestFilter {
//
//    private final RefreshTokenService refreshTokenService;
//    private final JwtTokenValidator jwtTokenValidator;
//    private final UserDetailsServiceImpl userDetailsService;
//
//    @Override
//    protected void doFilterInternal(
//        HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
//        throws ServletException, IOException {
//
//        String refreshToken = jwtTokenValidator.getJwtRefreshTokenFromHeader(req);
//
//        if (StringUtils.hasText(refreshToken) && jwtTokenValidator.validateToken(refreshToken, res)) {
//            checkRefreshTokenAndReIssueAccessToken(res, refreshToken);
//            return;
//        }
//
//        checkAccessTokenAndAuthentication(req, res, filterChain);
//    }
//
//    // 인증 처리
//    public void setAuthentication(String username) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication = createAuthentication(username);
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);
//    }
//
//    // 인증 객체 생성
//    private Authentication createAuthentication(String username) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//
//    private void checkAccessTokenAndAuthentication(HttpServletRequest request,
//        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String accessToken = jwtTokenValidator.getJwtAccessTokenFromHeader(request);
//
//        if (StringUtils.hasText(accessToken) && jwtTokenValidator.validateToken(accessToken,
//            response)) {
//
//            Claims claims = JwtTokenProvider.getUserInfoFromToken(accessToken);
//
//            if (claims.get(JwtTokenProvider.REFRESH_HEADER) != null) {
//                putRefreshTokenInAuthorization(response);
//            }
//
//            setAuthentication(claims.getSubject());
//
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private ResponseEntity<Map<String, Object>> checkRefreshTokenAndReIssueAccessToken(String refreshToken) {
//        try {
//            // Refresh Token을 사용하여 Access Token 재발급
//            refreshTokenService.reIssueAccessToken(refreshToken);
//
//            // 성공 응답 생성
//            Map<String, Object> responseBody = new HashMap<>();
//            responseBody.put("code", ResSuccessCode.ACCESS_TOKEN_GENERATED.getResCode());
//            responseBody.put("message", "Access Token이 재발급되었습니다.");
//
//            return ResponseEntity.ok(responseBody); // 200 OK 응답 반환
//        } catch (Exception e) {
//            // 예외 처리 (필요 시)
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("code", "ERROR_CODE"); // 적절한 에러 코드로 대체
//            errorResponse.put("message", e.getMessage());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // 500 응답 반환
//        }
//    }
//
//    private ResponseEntity<Map<String, Object>> putRefreshTokenInAuthorization() {
//        // 에러 응답 생성
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("code", TokenErrorCode.NOT_ACCESS_TOKEN.getResCode());
//        errorResponse.put("message", "Access Token이 존재하지 않습니다.");
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); // 401 Unauthorized 응답 반환
//    }
//}