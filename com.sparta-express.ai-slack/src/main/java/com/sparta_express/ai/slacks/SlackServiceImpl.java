package com.sparta_express.ai.slacks;

import com.sparta_express.ai.ais.AiRepository;
import com.sparta_express.ai.common.CustomException;
import com.sparta_express.ai.common.ErrorType;
import com.sparta_express.ai.core.Ai;
import com.sparta_express.ai.core.Slack;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
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
        // 전송 시간
        Timestamp sendTime = slackClient.sendMessage(requestDto);

        Ai ai = aiRepository.findById(requestDto.getAiId()).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_AI));

        Slack slack = Slack.of(requestDto, sendTime, ai);

        slackRepository.save(slack);

        return SlackResponseDto.from(slack);
    }
}
