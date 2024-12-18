package com.sparta_express.hub.domain.model;


import com.sparta_express.hub.Hub;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origin_hub_id", nullable = false, columnDefinition = "UUID")
    private final Hub originHub;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destination_hub_id", nullable = false, columnDefinition = "UUID")
    private final Hub destinationHub;

    @Column(nullable = false)
    private final int distanceKm;

    @Column(nullable = false)
    private final int estimatedMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final InterhubRouteStatus status;


    @Builder(toBuilder = true)
    public InterhubRoute(Hub originHub,
                         Hub destinationHub,
                         int distanceKm,
                         int estimatedMinutes,
                         InterhubRouteStatus status) {

        assert (!originHub.equals(destinationHub));
        assert (distanceKm > 0 && estimatedMinutes > 0);

        this.originHub = originHub;
        this.destinationHub = destinationHub;
        this.distanceKm = distanceKm;
        this.estimatedMinutes = estimatedMinutes;
        this.status = status;
    }

    public UUID getOriginHubId() {
        return originHub.getId();
    }

    public UUID getDestinationHubId() {
        return destinationHub.getId();
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof InterhubRoute)) return false;

        if (this == o || id == ((InterhubRoute) o).id) return true;

        return status == ((InterhubRoute) o).status
                && originHub.equals(((InterhubRoute) o).originHub)
                && destinationHub.equals(((InterhubRoute) o).destinationHub)
                && distanceKm == ((InterhubRoute) o).distanceKm
                && estimatedMinutes == ((InterhubRoute) o).estimatedMinutes;
    }
}
