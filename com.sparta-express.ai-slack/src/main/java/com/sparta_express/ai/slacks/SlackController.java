package com.sparta_express.ai.slacks;

import com.sparta_express.ai.common.ResponseDataDto;
import com.sparta_express.ai.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/slack/messages")
public class SlackController {

    private final SlackService slackService;

    /**
     * Slack 메시지 생성
     *
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseDataDto<SlackResponseDto>> sendMessage(
        @RequestBody SlackRequestDto requestDto) {
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CREATE_SLACK_SUCCESS,
            slackService.createMessage(requestDto)));
    }

}
