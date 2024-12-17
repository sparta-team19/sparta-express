package com.sparta_express.ai.slacks;

import com.fasterxml.jackson.databind.JsonNode;
import com.querydsl.core.types.Predicate;
import com.sparta_express.ai.ais.AiRepository;
import com.sparta_express.ai.common.CustomException;
import com.sparta_express.ai.common.ErrorType;
import com.sparta_express.ai.core.Ai;
import com.sparta_express.ai.core.Slack;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    private final AiRepository aiRepository;
    private final SlackRepository slackRepository;
    private final SlackClient slackClient;

    @Transactional
    @Override
    public SlackResponseDto createMessage(SlackRequestDto requestDto) {

        Ai ai = aiRepository.findById(requestDto.getAiId()).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_AI));

        // 메시지 만들기
        String message = createMessage(ai);

        // 응답 내용 받아오기
        JsonNode responseBody = slackClient.sendMessage(requestDto.getReceiverId(),
            message);
        // 전송 시간
        String unixTimestamp = responseBody.get("ts").asText();
        Timestamp sendTime = convertStringToTimestamp(unixTimestamp);

        // 채널Id
        String channelId = responseBody.get("channel").asText();

        Slack slack = Slack.of(requestDto.getReceiverId(), message, sendTime, ai, channelId, unixTimestamp);

        slackRepository.save(slack);

        return SlackResponseDto.from(slack);
    }

    @Transactional
    @Override
    public SlackResponseDto updateMessage(SlackRequestDto requestDto, UUID messageId) {
        Slack slack = slackRepository.findById(messageId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_SLACK));

        Ai ai = aiRepository.findById(requestDto.getAiId()).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_AI));
        
        // 메시지 만들기
        String message = createMessage(ai);

        slackClient.updateMessage(slack.getChannelId(), slack.getTs(), message);

        slack.updateSlack(message);

        return SlackResponseDto.from(slack);
    }

    private static @NotNull String createMessage(Ai ai) {
        return ai.getAiRequest() + "\n\n" + ai.getAiResponse();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SlackResponseDto> getMessages(Pageable pageable) {
        Page<Slack> slackpage = slackRepository.findAll(pageable);
        return slackpage.map(SlackResponseDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public SlackResponseDto getMessage(UUID messageId) {
        Slack slack = slackRepository.findById(messageId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_SLACK));

        return SlackResponseDto.from(slack);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SlackResponseDto> searchMessage(Predicate predicate, Pageable pageable) {
        Page<Slack> slackPage = slackRepository.findAll(predicate, pageable);
        return slackPage.map(SlackResponseDto::from);
    }

    @Transactional
    @Override
    public void deleteMessage(UUID messageId) {
        Slack slack = slackRepository.findById(messageId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_SLACK));

        boolean isDeleted = slackClient.deleteMessage(slack.getChannelId(), slack.getTs());

        if (isDeleted) {
            slack.setIsDeleted(Boolean.TRUE);
        } else {
            throw new CustomException(ErrorType.DELETE_SLACK_FAIL);
        }
    }

    private Timestamp convertStringToTimestamp(String unixTimestamp) {
        try {
            // 문자열을 double로 변환
            double unixTimestampDouble = Double.parseDouble(unixTimestamp);

            // 초와 나노초 분리
            long seconds = (long) unixTimestampDouble; // 정수 부분 (초)
            long nanoAdjustment = (long) ((unixTimestampDouble - seconds)
                * 1_000_000_000); // 소수점 이하 (나노초)

            // Timestamp 객체 생성: 초를 밀리초로 변환하여 생성
            return new Timestamp(seconds * 1000 + nanoAdjustment / 1_000_000); // 나노초를 밀리초로 변환하여 추가
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null; // 오류 발생 시 null 반환
        }
    }

    public static String convertTimestampToString(Timestamp timestamp) {
        // 밀리초를 초로 변환하고 나노초를 추가
        long millis = timestamp.getTime();
        double unixTimestamp = millis / 1000.0; // 초 단위로 변환

        // 문자열로 변환하여 반환
        return String.valueOf(unixTimestamp);
    }
}
