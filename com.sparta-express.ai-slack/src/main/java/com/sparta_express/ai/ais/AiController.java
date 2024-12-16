package com.sparta_express.ai.ais;

import com.querydsl.core.types.Predicate;
import com.sparta_express.ai.common.CustomException;
import com.sparta_express.ai.common.ErrorType;
import com.sparta_express.ai.common.ResponseDataDto;
import com.sparta_express.ai.common.ResponseMessageDto;
import com.sparta_express.ai.common.ResponseStatus;
import com.sparta_express.ai.core.Ai;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai/requests")
public class AiController {

    private final AiService aiService;

    /**
     * AI 요청 및 응답 생성
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseDataDto<?>> gemini(
        @Valid @RequestBody AiRequestDto requestDto) {
        try {
            return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CREATE_AI_SUCCESS,
                aiService.getContents(requestDto.getAiRequest())));
        } catch (HttpClientErrorException e) {
            throw new CustomException(ErrorType.HTTP_CLIENT_ERROR);
        }
    }

    /**
     * AI 요청 및 응답 조회
     * @param requestId
     * @return
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<ResponseDataDto<AiResponseDto>> getAiRequest(
        @PathVariable UUID requestId
    ) {
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.GET_AI_SUCCESS, aiService.getAiRequest(requestId)));
    }

    /**
     * AI 요청 및 응답 삭제
     * @param requestId
     * @param role
     * @return
     */
    @DeleteMapping("/{requestId}")
    public ResponseEntity<ResponseMessageDto> deleteAiRequest(
        @PathVariable UUID requestId,
        @RequestHeader(value = "X-Role", required = true) String role
    ) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        aiService.deleteAiRequest(requestId);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_AI_SUCCESS));
    }

    /**
     * AI 요청 및 응답 검색
     * @param userId
     * @param role
     * @param pageable
     * @param predicate
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDataDto<Page<AiResponseDto>>> searchAiRequest(
        @RequestHeader(value = "X-User-Id", required = true) String userId,
        @RequestHeader(value = "X-Role", required = true) String role,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        @QuerydslPredicate(root = Ai.class) Predicate predicate
    ) {
        if (!"MASTER".equals(role)) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.SEARCH_AI_SUCCESS,
            aiService.searchAiRequest(pageable, predicate)));
    }
}
