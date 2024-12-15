package com.sparta_express.auth.user.entity;

import com.sparta_express.auth.common.entity.BaseEntity;
import com.sparta_express.auth.user.dto.UserRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper.IntegerDescriptor;

@Entity
@Table(name = "p_delivery_managers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryManager extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private UUID hubId;

    @Column(nullable = false)
    private DeliveryType type;

    @Column(nullable = false)
    private int deliverySequence;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Setter(value = AccessLevel.NONE)
    private User user;

    @Builder
    private DeliveryManager(UUID hubId, DeliveryType type, int deliverySequence) {
        this.hubId = hubId;
        this.type = type;
        this.deliverySequence = deliverySequence;
    }

    public static DeliveryManager from(UserRequestDto requestDto) {
        return builder()
            .type(requestDto.getType())
            .build();
    }

    public static DeliveryManager of(UserRequestDto requestDto, int deliverySequence) {
        return builder()
            .type(requestDto.getType())
            .deliverySequence(deliverySequence)
            .build();
    }

    public void updateDeliveryManager(UserRequestDto requestDto) {
        builder()
            .type(requestDto.getType())
            .build();
    }
}
