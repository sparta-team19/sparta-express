package com.sparta_express.ai.slacks;

import com.sparta_express.ai.core.Ai;
import com.sparta_express.ai.core.Slack;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SlackResponseDto {

    private UUID slackMessageId;
    private String receiverId;
    private String message;
    private Timestamp sendTime;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    @Builder
    private SlackResponseDto(UUID slackMessageId, String receiverId, String message,
        Timestamp sendTime,
        LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        this.slackMessageId = slackMessageId;
        this.receiverId = receiverId;
        this.message = message;
        this.sendTime = sendTime;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static SlackResponseDto from(Slack slack) {
        return builder()
            .slackMessageId(slack.getId())
            .receiverId(slack.getReceiverId())
            .message(slack.getMessage())
            .sendTime(slack.getSendTime())
            .createdAt(slack.getCreatedAt())
            .createdBy(slack.getCreatedBy())
            .updatedAt(slack.getUpdatedAt())
            .updatedBy(slack.getUpdatedBy())
            .build();
    }
}
