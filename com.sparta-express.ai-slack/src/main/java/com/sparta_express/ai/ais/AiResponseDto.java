package com.sparta_express.ai.ais;

import com.sparta_express.ai.core.Ai;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiResponseDto {

    private UUID aiDeliveryTimeId;
    private String aiRequest;
    private String aiResponse;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    @Builder
    private AiResponseDto(UUID id, String aiRequest, String aiResponse, LocalDateTime createdAt,
        String createdBy, LocalDateTime updatedAt, String updatedBy) {
        this.aiDeliveryTimeId = id;
        this.aiRequest = aiRequest;
        this.aiResponse = aiResponse;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static AiResponseDto from(Ai ai) {
        return builder()
            .aiRequest(ai.getAiRequest())
            .aiResponse(ai.getAiResponse())
            .createdAt(ai.getCreatedAt())
            .createdBy(ai.getCreatedBy())
            .updatedAt(ai.getUpdatedAt())
            .updatedBy(ai.getUpdatedBy())
            .build();
    }

}
