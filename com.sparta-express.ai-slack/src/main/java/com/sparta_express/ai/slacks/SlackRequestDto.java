package com.sparta_express.ai.slacks;

import java.util.UUID;
import lombok.Getter;

@Getter
public class SlackRequestDto {

    private UUID aiId;
    private String receiverId;
}
