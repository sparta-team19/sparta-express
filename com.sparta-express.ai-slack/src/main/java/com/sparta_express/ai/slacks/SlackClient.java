package com.sparta_express.ai.slacks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class SlackClient {
    private final RestTemplate restTemplate;

    @Value("${slack.bot.token}")
    private String botToken;

    private static final String POST_MESSAGE_URL = "https://slack.com/api/chat.postMessage";

    /**
     * Slack 사용자에게 메시지를 전송하는 메서드
     *
     * @param requestDto
     */
    public Timestamp sendMessage(SlackRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + botToken);
        headers.set("Content-Type", "application/json");

        String body = String.format(
            "{\"channel\":\"%s\", \"text\":\"%s\"}",
            requestDto.getReceiverId(), requestDto.getMessage()
        );

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            POST_MESSAGE_URL,
            HttpMethod.POST,
            request,
            String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Slack 메시지 전송 실패: " + response.getBody());
        }

        // 메시지 전송이 성공적으로 실행되면 응답에서 전송시간을 추출
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseBody = objectMapper.readTree(response.getBody());
            if (responseBody.get("ok").asBoolean()) {
                String ts = responseBody.get("ts").asText(); // 메시지 전송 시간(ts)
                return getTimestamp(ts);
            } else {
                throw new RuntimeException("Slack API 응답 실패: " + responseBody.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Slack API 응답 파싱 실패: " + e.getMessage(), e);
        }
    }

    private Timestamp getTimestamp(String ts) {
        Timestamp unixTimestampMillis = getTimestamp(ts); // 위에서 만든 메서드 사용
        return unixTimestampMillis;
    }
}
