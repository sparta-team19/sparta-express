package com.sparta_express.order_shipment.domain.entity;

import com.sparta_express.order_shipment.application.dto.ShipmentRouteUpdateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_shipment_routes")
public class ShipmentRoute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private UUID originHubId;

    @Column(nullable = false)
    private UUID destinationHubId;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum status = ShipmentStatusEnum.HUB_WAITING;

    @Column(nullable = false)
    private Integer estimatedDistance;

    @Column(nullable = false)
    private Integer estimatedTime;

    private Integer actualDistance;

    private Integer actualTime;

    @Column(nullable = false)
    private UUID deliveryManagerId;

    @Column(nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @Builder
    private ShipmentRoute(Integer sequence, UUID originHubId, UUID destinationHubId,
                          String deliveryAddress, Integer estimatedDistance, Integer estimatedTime,
                          UUID deliveryManagerId, String userId, Shipment shipment) {
        this.sequence = sequence;
        this.originHubId = originHubId;
        this.destinationHubId = destinationHubId;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.deliveryManagerId = deliveryManagerId;
        this.userId = userId;
        this.shipment = shipment;
    }

    public static ShipmentRoute of(Integer sequence, UUID originHubId, UUID destinationHubId,
                                   String deliveryAddress, Integer estimatedDistance, Integer estimatedTime,
                                   UUID deliveryManagerId, String userId, Shipment shipment) {
        return ShipmentRoute.builder()
                .sequence(sequence)
                .originHubId(originHubId)
                .destinationHubId(destinationHubId)
                .deliveryAddress(deliveryAddress)
                .estimatedDistance(estimatedDistance)
                .estimatedTime(estimatedTime)
                .deliveryManagerId(deliveryManagerId)
                .userId(userId)
                .shipment(shipment)
                .build();
    }

    public void update(ShipmentRouteUpdateDto updateDto, String userId) {
        updateSequence(updateDto.getSequence());
        updateOriginHubId(updateDto.getOriginHubId());
        updateDestinationHubId(updateDto.getDestinationHubId());
        updateDeliveryAddress(updateDto.getDeliveryAddress());
        updateStatus(updateDto.getStatus());
        updateEstimatedDistance(updateDto.getEstimatedDistance());
        updateEstimatedTime(updateDto.getEstimatedTime());
        updateActualDistance(updateDto.getActualDistance());
        updateActualTime(updateDto.getActualTime());
        updateDeliveryManagerId(updateDto.getDeliveryManagerId());
        updateUserId(userId);
    }

    private void updateSequence(Integer sequence) {
        if (sequence != null) {
            this.sequence = sequence;
        }
    }

    private void updateOriginHubId(UUID originHubId) {
        if (originHubId != null) {
            this.originHubId = originHubId;
        }
    }

    private void updateDestinationHubId(UUID destinationHubId) {
        if (destinationHubId != null) {
            this.destinationHubId = destinationHubId;
        }
    }

    private void updateDeliveryAddress(String deliveryAddress) {
        if (deliveryAddress != null && !deliveryAddress.trim().isEmpty()) {
            this.deliveryAddress = deliveryAddress;
        }
    }

    private void updateStatus(ShipmentStatusEnum status) {
        if (status != null) {
            this.status = status;
        }
    }

    private void updateEstimatedDistance(Integer estimatedDistance) {
        if (estimatedDistance != null) {
            this.estimatedDistance = estimatedDistance;
        }
    }

    private void updateEstimatedTime(Integer estimatedTime) {
        if (estimatedTime != null) {
            this.estimatedTime = estimatedTime;
        }
    }

    private void updateActualDistance(Integer actualDistance) {
        if (actualDistance != null) {
            this.actualDistance = actualDistance;
        }
    }

    private void updateActualTime(Integer actualTime) {
        if (actualTime != null) {
            this.actualTime = actualTime;
        }
    }

    private void updateDeliveryManagerId(UUID deliveryManagerId) {
        if (deliveryManagerId != null) {
            this.deliveryManagerId = deliveryManagerId;
        }
    }

    private void updateUserId(String userId) {
        if (userId != null) {
            this.userId = userId;
        }
    }

}
