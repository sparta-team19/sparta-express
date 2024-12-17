package com.sparta_express.ai.ais;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AiClient {

    private final RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public AiClient(@Qualifier("geminiRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeminiResponseDto getResponse(String prompt) {
        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        GeminiRequestDto request = new GeminiRequestDto(prompt + " 요청 정보를 모두 고려하여 이 때까진 보내야 납기에 맞출 수 있다 하는 마지막 시점. 즉, 최종 발송 시한을 최대한 낙관적으로 예시처럼 답변.\n"
            + "예시 - 위 내용을 기반으로 도출된 최종 발송 시한은 12월 10일 오전 9시 입니다.");
        return restTemplate.postForObject(requestUrl, request, GeminiResponseDto.class);
    }
}
