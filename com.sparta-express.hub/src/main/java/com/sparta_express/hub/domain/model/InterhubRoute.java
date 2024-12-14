package com.sparta_express.hub.domain.model;


import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class InterhubRoute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private final UUID originHubId;

    @Column(nullable = false)
    private final UUID destinationHubId;

    @Column(nullable = false)
    private final int distanceKm;

    @Column(nullable = false)
    private final int estimatedMinutes;

    @Column(nullable = false)
    private final InterhubRouteStatus status;


    @Builder(toBuilder = true)
    public InterhubRoute(UUID originHubId,
                         UUID destinationHubId,
                         int distanceKm,
                         int estimatedMinutes,
                         InterhubRouteStatus status) {

        assert (originHubId != null
                && destinationHubId != null
                && distanceKm > 0
                && estimatedMinutes > 0
                && status != null);

        this.originHubId = originHubId;
        this.destinationHubId = destinationHubId;
        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
        this.status = status;
    }
}
