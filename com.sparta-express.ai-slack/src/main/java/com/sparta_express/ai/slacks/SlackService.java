package com.sparta_express.ai.slacks;

import java.util.UUID;

public interface SlackService {

    SlackResponseDto createMessage(SlackRequestDto requestDto);

    SlackResponseDto updateMessage(SlackRequestDto requestDto, UUID messageId);
}
