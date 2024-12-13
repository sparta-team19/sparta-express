//package com.sparta_express.auth.config;
//
//import com.sparta_express.auth.jwt.JwtTokenValidator;
//import com.sparta_express.auth.jwt.RefreshTokenService;
//import com.sparta_express.auth.security.filter.JwtAuthenticationFilter;
//import com.sparta_express.auth.security.handler.CustomAccessDeniedHandler;
//import com.sparta_express.auth.security.handler.UserDetailsServiceImpl;
//import jakarta.servlet.Filter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final RefreshTokenService refreshTokenService;
//    private final JwtTokenValidator jwtTokenValidator;
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//    private final UserDetailsServiceImpl userDetailsService;
//    private final AuthenticationConfiguration authenticationConfiguration;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
//            throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public Filter jwtAuthenticationFilter() throws Exception {
//        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(refreshTokenService);
//        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
//        return filter;
//    }
//
////    @Bean
////    public JwtAuthorizationFilter jwtAuthorizationFilter() {
////        return new JwtAuthorizationFilter(refreshTokenService, jwtTokenValidator, userDetailsService);
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // CSRF 설정
//        http.csrf((csrf) -> csrf.disable())
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//        ;
//
//        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
//        http.sessionManagement((sessionManagement) ->
//                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//
//        http.authorizeHttpRequests((authorizeHttpRequests) ->
//                authorizeHttpRequests
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                        .permitAll() // resources 접근 허용 설정
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/api/v1/users/signup", "/api/v1/users/login").permitAll()
////                        .requestMatchers("/admin/v1/**").hasAnyRole("MASTER", "MANAGER")
//                        .anyRequest().authenticated()
//        );
//
//        http.exceptionHandling((exception) ->
//                exception.accessDeniedHandler(accessDeniedHandler));
//
//        // 필터 관리
////        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}