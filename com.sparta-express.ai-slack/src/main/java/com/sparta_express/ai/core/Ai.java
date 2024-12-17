package com.sparta_express.ai.core;

import com.sparta_express.ai.ais.AiRequestDto;
import com.sparta_express.ai.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_ai_delivery_times")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ai extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String aiRequest;

    @Column(nullable = false)
    private String aiResponse;

    @Builder
    private Ai(UUID id, String aiRequest, String aiResponse) {
        this.id = id;
        this.aiRequest = aiRequest;
        this.aiResponse = aiResponse;
    }

    public static Ai of(String prompt, String message) {
        return builder()
            .aiRequest(prompt)
            .aiResponse(message)
            .build();
    }
}