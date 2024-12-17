package com.sparta_express.ai.slacks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @Value("${slack.bot.token}")
    private String botToken;

    private static final String POST_MESSAGE_URL = "https://slack.com/api/chat.postMessage";
    private static final String UPDATE_MESSAGE_URL = "https://slack.com/api/chat.update";
    private static final String DELETE_MESSAGE_URL = "https://slack.com/api/chat.delete";

    /**
     * Slack 사용자에게 메시지를 전송하는 메서드
     *
     * @param requestDto
     */
    public JsonNode sendMessage(SlackRequestDto requestDto) {
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
                return responseBody; // 메시지 전송 시간(ts)

            } else {
                throw new RuntimeException("Slack API 응답 실패: " + responseBody.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Slack API 응답 파싱 실패: " + e.getMessage(), e);
        }
    }

    /**
     * Slack 메시지 수정
     *
     * @param channelId
     * @param timestamp
     * @param newMessage
     */
    public void updateMessage(String channelId, String timestamp, String newMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + botToken);
        headers.set("Content-Type", "application/json");

        // 요청 바디 생성
        String body = String.format(
            "{\"channel\":\"%s\", \"ts\":\"%s\", \"text\":\"%s\"}",
            channelId, timestamp, newMessage
        );

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // Slack API 호출
        ResponseEntity<String> response = restTemplate.exchange(
            UPDATE_MESSAGE_URL,
            HttpMethod.POST,
            request,
            String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Slack 메시지 수정 실패: " + response.getBody());
        }

        try {
            JsonNode responseBody = objectMapper.readTree(response.getBody());

            if (!responseBody.get("ok").asBoolean()) {
                throw new RuntimeException("Slack API 응답 실패: " + responseBody.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Slack API 응답 파싱 실패: " + e.getMessage(), e);
        }
    }

    /**
     * Slack 메시지 삭제
     *
     * @param channelId 메시지가 있는 채널 ID
     * @param timestamp 메시지의 타임스탬프
     */
    public boolean deleteMessage(String channelId, String timestamp) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + botToken);
        headers.set("Content-Type", "application/json");

        // 요청 바디 구성
        String body = String.format(
            "{\"channel\":\"%s\", \"ts\":\"%s\"}",
            channelId, timestamp
        );

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // Slack API 호출
        ResponseEntity<String> response = restTemplate.exchange(
            DELETE_MESSAGE_URL,
            HttpMethod.POST,
            request,
            String.class
        );

        // JSON 응답 확인
        try {
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            boolean isOk = jsonResponse.get("ok").asBoolean();
            if (!isOk) {
                String error = jsonResponse.get("error").asText();
                throw new RuntimeException("Slack 메시지 삭제 실패: " + error);
            }
            return isOk;
        } catch (Exception e) {
            throw new RuntimeException("Slack 메시지 삭제 중 오류 발생", e);
        }
    }
}
