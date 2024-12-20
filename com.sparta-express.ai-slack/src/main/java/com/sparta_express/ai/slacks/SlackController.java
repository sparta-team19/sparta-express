package com.sparta_express.ai.slacks;

import com.querydsl.core.types.Predicate;
import com.sparta_express.ai.common.CustomException;
import com.sparta_express.ai.common.ErrorType;
import com.sparta_express.ai.common.ResponseDataDto;
import com.sparta_express.ai.common.ResponseMessageDto;
import com.sparta_express.ai.common.ResponseStatus;
import com.sparta_express.ai.core.Ai;
import com.sparta_express.ai.core.Slack;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    /**
     * Slack 메시지 조회
     *
     * @param role
     * @param pageable
     * @return
     */
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

    /**
     * Slack 메시지 단일 조회
     *
     * @param role
     * @param messageId
     * @return
     */
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

    /**
     * Slack 메시지 검색
     *
     * @param role
     * @param pageable
     * @param predicate
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDataDto<Page<SlackResponseDto>>> searchMessage(
        @RequestHeader(value = "X-Role", required = true) String role,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        @QuerydslPredicate(root = Slack.class) Predicate predicate
    ) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.SEARCH_SLACK_SUCCESS,
            slackService.searchMessage(predicate, pageable)));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ResponseMessageDto> deleteSlack(
        @RequestHeader(value = "X-Role", required = true) String role,
        @PathVariable UUID messageId
    ) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        slackService.deleteMessage(messageId);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_SLACK_SUCCESS));
    }
}
