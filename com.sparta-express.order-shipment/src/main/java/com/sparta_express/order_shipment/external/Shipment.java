package com.sparta_express.order_shipment.external;

import com.sparta_express.company_product.external.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_shipments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipment extends com.sparta_express.order_shipment.common.BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "origin_hub_id", nullable = false)
    private Hub originHub;

    @ManyToOne
    @JoinColumn(name = "destination_hub_id", nullable = false)
    private Hub destinationHub;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_slack_id")
    private String recipientSlackId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Builder
    public Shipment(Order order, Hub originHub, Hub destinationHub, String deliveryAddress,
                    String recipientName, String recipientSlackId, ShipmentStatus status) {
        this.order = order;
        this.originHub = originHub;
        this.destinationHub = destinationHub;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
        this.recipientSlackId = recipientSlackId;
        this.status = status;
    }

    public static Shipment of(Order order, Hub originHub, Hub destinationHub,
                              String deliveryAddress, String recipientName,
                              String recipientSlackId, ShipmentStatus status) {
        return Shipment.builder()
                .order(order)
                .originHub(originHub)
                .destinationHub(destinationHub)
                .deliveryAddress(deliveryAddress)
                .recipientName(recipientName)
                .recipientSlackId(recipientSlackId)
                .status(status)
                .build();
    }

}
