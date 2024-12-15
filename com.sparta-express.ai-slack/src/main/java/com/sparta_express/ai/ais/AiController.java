package com.sparta_express.ai.ais;

import com.sparta_express.ai.common.CustomException;
import com.sparta_express.ai.common.ErrorType;
import com.sparta_express.ai.common.ResponseDataDto;
import com.sparta_express.ai.common.ResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping("/request")
    public ResponseEntity<ResponseDataDto<?>> gemini(
        @Valid @RequestBody AiRequestDto requestDto) {
        try {
            return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CREATE_AI_SUCCESS,
                aiService.getContents(requestDto.getAiRequest())));
        } catch (HttpClientErrorException e) {
            throw new CustomException(ErrorType.HTTP_CLIENT_ERROR);
        }
    }
}
