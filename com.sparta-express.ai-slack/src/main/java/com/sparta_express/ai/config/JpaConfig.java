package com.sparta_express.ai.config;

import com.sparta_express.ai.common.auditing.SoftDeleteEntityListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public SoftDeleteEntityListener softDeleteEntityListener(AuditorAware<String> auditorAware) {
        return new SoftDeleteEntityListener(auditorAware);
    }

}