package com.sparta_express.auth.user.entity;

import com.sparta_express.auth.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column
    private DeliveryType type;

    @Column
    private Integer deliverySequence;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Setter(value = AccessLevel.NONE)
    private User user;
}
