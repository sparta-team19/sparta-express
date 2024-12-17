package com.sparta_express.ai.slacks;

import com.querydsl.core.types.Predicate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackService {

    SlackResponseDto createMessage(SlackRequestDto requestDto);

    SlackResponseDto updateMessage(SlackRequestDto requestDto, UUID messageId);

    Page<SlackResponseDto> getMessages(Pageable pageable);

    SlackResponseDto getMessage(UUID messageId);

    Page<SlackResponseDto> searchMessage(Predicate predicate, Pageable pageable);

    void deleteMessage(UUID messageId);
}
