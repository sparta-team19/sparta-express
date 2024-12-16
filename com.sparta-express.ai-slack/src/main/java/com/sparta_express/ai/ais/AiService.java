package com.sparta_express.ai.ais;

import java.util.UUID;

public interface AiService {

    AiResponseDto getContents(String prompt);

    AiResponseDto getAiRequest(UUID requestId);

    void deleteAiRequest(UUID requestId);
}
