package com.sparta_express.ai.core;

import com.sparta_express.ai.common.entity.BaseEntity;
import com.sparta_express.ai.slacks.SlackRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_slack_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Slack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    String receiverId;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    Timestamp sendTime;

    @Column(nullable = false)
    String channelId;   // 슬랙 메시지의 채널 id

    @Column(nullable = false)
    String ts;  // 슬랙 메시지의 Unix Timestamp

    @OneToOne
    @JoinColumn(name = "ai_delivery_time_id", nullable = false)
    @Setter(value = AccessLevel.NONE)
    private Ai ai;

    @Builder
    private Slack(String receiverId, String message, Timestamp sendTime, Ai ai, String channelId, String ts) {
        this.receiverId = receiverId;
        this.message = message;
        this.sendTime = sendTime;
        this.ai = ai;
        this.channelId = channelId;
        this.ts = ts;
    }

    public static Slack of(String receiverId, String message, Timestamp sendTime, Ai ai, String channelId, String ts) {
        return builder()
            .receiverId(receiverId)
            .message(message)
            .sendTime(sendTime)
            .ai(ai)
            .channelId(channelId)
            .ts(ts)
            .build();
    }

    public void updateSlack(String message) {
        this.message = message;
    }
}
