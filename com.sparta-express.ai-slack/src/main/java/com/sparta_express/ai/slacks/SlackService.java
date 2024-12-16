package com.sparta_express.ai.slacks;

public interface SlackService {

    SlackResponseDto createMessage(SlackRequestDto requestDto);
}
