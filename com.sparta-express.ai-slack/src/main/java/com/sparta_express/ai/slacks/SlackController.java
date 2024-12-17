package com.sparta_express.ai.slacks;

import com.sparta_express.ai.common.CustomException;
import com.sparta_express.ai.common.ErrorType;
import com.sparta_express.ai.common.ResponseDataDto;
import com.sparta_express.ai.common.ResponseStatus;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    /**
     * 슬랙 메시지 수정
     *
     * @param requestDto
     * @param messageId
     * @param role
     * @return
     */
    @PutMapping("/{messageId}")
    public ResponseEntity<ResponseDataDto<SlackResponseDto>> updateMessage(
        @RequestBody SlackRequestDto requestDto,
        @PathVariable UUID messageId,
        @RequestHeader(value = "X-Role", required = true) String role
    ) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.UPDATE_SLACK_SUCCESS,
            slackService.updateMessage(requestDto, messageId)));
    }

    @GetMapping
    public ResponseEntity<ResponseDataDto<Page<SlackResponseDto>>> getMessages(
        @RequestHeader(value = "X-Role", required = true) String role,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.GET_SLACK_SUCCESS,
            slackService.getMessages(pageable)));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<ResponseDataDto<SlackResponseDto>> getMessage(
        @RequestHeader(value = "X-Role", required = true) String role,
        @PathVariable UUID messageId
    ) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.GET_SLACK_SUCCESS,
            slackService.getMessage(messageId)));
    }
}
