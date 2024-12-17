package com.sparta_express.hub.infrastructure.repository;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        String userId = "testUser";

        return Optional.of(userId);
    }

}