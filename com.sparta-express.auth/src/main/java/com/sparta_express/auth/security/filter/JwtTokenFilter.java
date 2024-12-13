//package com.sparta_express.auth.security.filter;
//
//import com.sparta_express.auth.jwt.JwtTokenValidator;
//import com.sparta_express.auth.jwt.RefreshToken;
//import com.sparta_express.auth.jwt.RefreshTokenRepository;
//import com.sparta_express.auth.user.dto.UserRequestDto;
//import com.sparta_express.auth.user.entity.User;
//import com.sparta_express.auth.user.repository.UserRepository;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.CookieGenerator;
//
//@Slf4j
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtTokenValidator jwtTokenValidator; // JWT 검증 서비스
//
//    @Autowired
//    private UserDetailsService userDetailsService; // 사용자 세부 정보 서비스
//
//    @Autowired
//    private UserRepository userRepository; // user 리포지토리
//
//    @Autowired
//    private RefreshTokenRepository refreshTokenRepository; // Refresh Token 리포지토리
//
//    private final String AUTH_HEADER = "Authorization"; // 헤더 이름
//    private final String COOKIE_NAME = "token"; // 쿠키 이름
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//        throws ServletException, IOException {
//        String authorizationHeader = request.getHeader(AUTH_HEADER);
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.substring(7); // "Bearer " 부분 제거
//
//            // Access Token 검증
//            User found;
//
//            try {
//                // Access Token 유효성 검증
//                jwtTokenValidator.validateToken(token, response); // 유효성 검증 (예외 발생 시 아래로 넘어감)
//
//                // 사용자 정보 로드
//                found = userRepository.findByEmail(JwtTokenUtil.getEmail(token, jwtTokenValidator.getKey()))
//                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
//
//            } catch (ExpiredJwtException e) {
//                log.error("💡 Access Token이 만료되었습니다.");
//
//                // 만료된 Access Token으로 Redis에서 Refresh Token 정보 조회
//                RefreshToken foundTokenInfo = refreshTokenRepository.findByAccessToken(token)
//                    .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));
//
//                String refreshToken = foundTokenInfo.getRefreshToken();
//
//                // Refresh Token 유효성 검증
//                jwtTokenValidator.validateToken(refreshToken); // 유효성 검증 (예외 발생 시 아래로 넘어감)
//
//                // Refresh Token에 저장된 사용자 ID로 Employee 정보 조회
//                Long employeeId = Long.valueOf(foundTokenInfo.getId());
//                found = employeeRepository.findById(employeeId)
//                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
//
//                // 새로운 Access Token 생성
//                token = JwtTokenUtil.createToken(found.getAccount(), found.getEmail(), jwtTokenValidator.getKey());
//
//                // Redis에 새로운 Access Token 저장
//                refreshTokenRepository.save(new RefreshToken(String.valueOf(employeeId), refreshToken, token));
//
//                // 클라이언트 측 쿠키에 새로운 Access Token 업데이트
//                CookieGenerator cookieGenerator = new CookieGenerator();
//                cookieGenerator.setCookieName(COOKIE_NAME);
//                cookieGenerator.setCookieHttpOnly(true);
//                cookieGenerator.addCookie(response, token);
//                cookieGenerator.setCookieMaxAge(60 * 60); // 1시간
//            }
//
//            // Access Token이 유효한 경우 SecurityContext에 설정
//            if (found != null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(found.getEmail());
//                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(userDetails);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        // 다음 필터로 요청 진행
//        filterChain.doFilter(request, response);
//    }
//}