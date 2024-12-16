package com.sparta_express.ai.ais;

import com.sparta_express.ai.common.CustomException;
import com.sparta_express.ai.common.ErrorType;
import com.sparta_express.ai.core.Ai;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService{

    private final AiRepository aiRepository;
    private final AiClient aiClient;

    @Transactional
    @Override
    public AiResponseDto getContents(String prompt) {

        GeminiResponseDto response = aiClient.getResponse(prompt);

        String message = response.getCandidates().get(0).getContent().getParts().get(0).getText();

        Ai ai = Ai.of(prompt, message);

        aiRepository.save(ai);

        return AiResponseDto.from(ai);
    }

    @Transactional(readOnly = true)
    @Override
    public AiResponseDto getAiRequest(UUID requestId) {
        Ai ai = aiRepository.findById(requestId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_AI));

        return AiResponseDto.from(ai);
    }
}
