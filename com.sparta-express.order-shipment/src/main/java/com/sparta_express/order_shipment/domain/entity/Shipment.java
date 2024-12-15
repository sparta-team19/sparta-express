package com.sparta_express.order_shipment.domain.entity;

import com.sparta_express.order_shipment.application.dto.ShipmentUpdateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_shipments")
public class Shipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID originHubId;

    @Column(nullable = false)
    private UUID destinationHubId;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private String recipientName;

    private String recipientSlackId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum status = ShipmentStatusEnum.HUB_WAITING;

    @OneToOne(mappedBy = "shipment")
    private Order order;

    @Column(nullable = false)
    private String userId;

    private UUID companyDeliveryManagerId;

    @OneToMany(mappedBy = "shipment",cascade = CascadeType.PERSIST)
    private List<ShipmentRoute> shipmentRoutes = new ArrayList<>();

    @Builder
    private Shipment(UUID originHubId, UUID destinationHubId,
                     String deliveryAddress, String recipientName,
                     String recipientSlackId, Order order, String userId) {
        this.originHubId = originHubId;
        this.destinationHubId = destinationHubId;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
        this.recipientSlackId = recipientSlackId;
        this.order = order;
        this.userId = userId;
    }

    public static Shipment of(UUID originHubId, UUID destinationHubId,
                              String deliveryAddress, String recipientName,
                              String recipientSlackId, Order order, String userId) {
        return Shipment.builder()
                .originHubId(originHubId)
                .destinationHubId(destinationHubId)
                .deliveryAddress(deliveryAddress)
                .recipientName(recipientName)
                .recipientSlackId(recipientSlackId)
                .order(order)
                .userId(userId)
                .build();
    }

    public void update(ShipmentUpdateDto updateDto, String userId) {
        updateOriginHubId(updateDto.getOriginHubId());
        updateDestinationHubId(updateDto.getDestinationHubId());
        updateDeliveryAddress(updateDto.getDeliveryAddress());
        updateRecipientName(updateDto.getRecipientName());
        updateRecipientSlackId(updateDto.getRecipientSlackId());
        updateStatus(updateDto.getStatus());
        updateUserId(userId);
    }

    public void updateShipmentRoutes(List<ShipmentRoute> shipmentRoutes) {
        this.shipmentRoutes.addAll(shipmentRoutes);
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

    private void updateRecipientName(String recipientName) {
        if (recipientName != null && !recipientName.trim().isEmpty()) {
            this.recipientName = recipientName;
        }
    }

    private void updateRecipientSlackId(String recipientSlackId) {
        if (recipientSlackId != null && !recipientSlackId.trim().isEmpty()) {
            this.recipientSlackId = recipientSlackId;
        }
    }

    private void updateStatus(ShipmentStatusEnum status) {
        if (status != null) {
            this.status = status;
        }
    }

    private void updateUserId(String userId) {
        if (userId != null) {
            this.userId = userId;
        }
    }

}
