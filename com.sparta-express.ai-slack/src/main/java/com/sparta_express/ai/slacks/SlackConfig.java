package com.sparta_express.ai.slacks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfig {

    @Bean
    public String slackBotToken() {
        return System.getenv("SLACK_BOT_TOKEN");
    }
}
