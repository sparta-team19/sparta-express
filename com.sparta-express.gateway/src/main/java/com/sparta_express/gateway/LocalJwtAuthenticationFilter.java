package com.sparta_express.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component("localJwtAuthenticationFilter")
public class LocalJwtAuthenticationFilter implements GlobalFilter {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.equals("/api/v1/users/signup") || path.equals("/api/v1/users/login")) {
            return chain.filter(exchange);  // /signup, /login 경로는 필터를 적용하지 않음
        }

        String token = extractToken(exchange);

        if (token == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // validateAndModifyExchange 호출
        return validateAndModifyExchange(token, exchange, chain);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> validateAndModifyExchange(String token, ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token);
            log.info("#####payload :: " + claimsJws.getPayload().toString());
            Claims claims = claimsJws.getPayload();

            // 요청을 수정
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", claims.get("user_id").toString())
                .header("X-Role", claims.get("role").toString())
                .build();

            // 수정된 요청으로 새로운 Exchange 생성
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

            // 수정된 Exchange로 필터 체인 진행
            return chain.filter(modifiedExchange);
        } catch (Exception e) {
            // 인증 실패 시 Unauthorized 응답 반환
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

}