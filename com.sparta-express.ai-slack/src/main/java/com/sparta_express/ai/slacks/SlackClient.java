package com.sparta_express.ai.slacks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
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
     * @param receiverId
     * @param message
     */
    public JsonNode sendMessage(String receiverId, String message) {
        try {
            Map<String, String> requestBody = Map.of(
                "channel", receiverId,
                "text", message
            );

            HttpHeaders headers = createHeaders();

            // ObjectMapper를 사용해 JSON 문자열로 변환
            String body = objectMapper.writeValueAsString(requestBody);

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                POST_MESSAGE_URL,
                HttpMethod.POST,
                request,
                String.class
            );

            return handleResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("Slack 메시지 전송 중 오류 발생", e);
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
        try {
            // 요청 바디를 Map으로 생성
            Map<String, String> requestBody = Map.of(
                "channel", channelId,
                "ts", timestamp,
                "text", newMessage
            );
            HttpHeaders headers = createHeaders();

            String body = objectMapper.writeValueAsString(requestBody);

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            // Slack API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                UPDATE_MESSAGE_URL,
                HttpMethod.POST,
                request,
                String.class
            );

            // 응답 처리
            handleResponse(response);

        } catch (Exception e) {
            throw new RuntimeException("Slack 메시지 수정 중 오류 발생", e);
        }
    }

    /**
     * Slack 메시지 삭제
     *
     * @param channelId 메시지가 있는 채널 ID
     * @param timestamp 메시지의 타임스탬프
     */
    public boolean deleteMessage(String channelId, String timestamp) {
        try {
            // 요청 바디 구성
            Map<String, String> requestBody = Map.of(
                "channel", channelId,
                "ts", timestamp
            );

            HttpHeaders headers = createHeaders();

            String body = objectMapper.writeValueAsString(requestBody);

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            // Slack API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                DELETE_MESSAGE_URL,
                HttpMethod.POST,
                request,
                String.class
            );

            JsonNode jsonResponse = handleResponse(response);
            return jsonResponse.get("ok").asBoolean();
        } catch (Exception e) {
            throw new RuntimeException("Slack 메시지 삭제 중 오류 발생", e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + botToken);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    private JsonNode handleResponse(ResponseEntity<String> response) {
        try {
            JsonNode responseBody = objectMapper.readTree(response.getBody());
            if (!responseBody.get("ok").asBoolean()) {
                String error = responseBody.has("error") ? responseBody.get("error").asText() : "Unknown error";
                throw new RuntimeException("Slack API 실패: " + error);
            }
            return responseBody;
        } catch (Exception e) {
            throw new RuntimeException("Slack API 응답 파싱 실패", e);
        }
    }
}
