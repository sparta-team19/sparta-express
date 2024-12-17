package com.sparta_express.ai.slacks;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackService {

    SlackResponseDto createMessage(SlackRequestDto requestDto);

    SlackResponseDto updateMessage(SlackRequestDto requestDto, UUID messageId);

    Page<SlackResponseDto> getMessages(Pageable pageable);
}
