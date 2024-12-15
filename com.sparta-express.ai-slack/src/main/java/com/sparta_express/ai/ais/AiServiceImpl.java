package com.sparta_express.ai.ais;

import com.sparta_express.ai.core.Ai;
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
}
