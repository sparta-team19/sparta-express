package com.sparta_express.auth.common.auditing;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "auditorAware")
@RequiredArgsConstructor
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        // 회원가입시 가지고 email 가지고 온다.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if (request.getRequestURI().equals("/api/v1/users/signup")) {

            String signUpEmail = AuditorContext.getCurrentEmail();
            AuditorContext.clear();

            return Optional.of(signUpEmail);
        }
        return Optional.empty();
    }
}
