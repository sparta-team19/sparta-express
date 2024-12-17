package com.sparta_express.ai.ais;

import com.querydsl.core.types.Predicate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiService {

    AiResponseDto getContents(String prompt);

    AiResponseDto getAiRequest(UUID requestId);

    void deleteAiRequest(UUID requestId);

    Page<AiResponseDto> searchAiRequest(Pageable pageable, Predicate predicate);
}
