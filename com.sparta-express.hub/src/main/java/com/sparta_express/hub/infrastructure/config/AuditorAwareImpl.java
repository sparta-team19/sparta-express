package com.sparta_express.hub.infrastructure.config;

import com.sparta_express.hub.common.utils.RequestExtractor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        String userId
                = RequestExtractor.extractHeaderOf("X-User-Id");

        return Optional.of(userId);
    }

}