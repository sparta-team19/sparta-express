package com.sparta_express.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta_express.auth.common.CustomException;
import com.sparta_express.auth.common.ErrorType;
import com.sparta_express.auth.common.ResponseMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final String ROLE_PREFIX = "ROLE_";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException ex)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = "";
        if (auth != null) {
            authorities = auth.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.joining(", "))
                    .replace(ROLE_PREFIX, "");
        }

        CustomException commonResponse = new CustomException(ErrorType.ACCESS_DENIED);

        new ObjectMapper().writeValue(response.getOutputStream(), commonResponse);
    }
}
