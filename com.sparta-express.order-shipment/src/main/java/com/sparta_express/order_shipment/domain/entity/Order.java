package com.sparta_express.order_shipment.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID requesterId;

    @Column(nullable = false)
    private UUID receiverId;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate dueDate;

    private String requestDetails;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus = OrderStatusEnum.PENDING;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @Column(nullable = false)
    private String userId;

    @Builder
    private Order(UUID requesterId, UUID receiverId, UUID productId,
                  Integer quantity, LocalDate dueDate,
                  String requestDetails, String userId) {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.productId = productId;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.requestDetails = requestDetails;
        this.userId = userId;
    }

    public static Order of(UUID requesterId, UUID receiverId,
                           UUID productId, Integer quantity, LocalDate dueDate,
                           String requestDetails, String userId) {
        return Order.builder()
                .requesterId(requesterId)
                .receiverId(receiverId)
                .productId(productId)
                .quantity(quantity)
                .requestDetails(requestDetails)
                .dueDate(dueDate)
                .userId(userId)
                .build();
    }

    public void update(Integer quantity, LocalDate dueDate, String requestDetails, OrderStatusEnum orderStatus) {
        if (quantity != null) this.quantity = quantity;
        if (dueDate != null) this.dueDate = dueDate;
        if (requestDetails != null) this.requestDetails = requestDetails;
        if (orderStatus != null) this.orderStatus = orderStatus;
    }

    public void updateShipment(Shipment shipment) {
        this.shipment = shipment;
    }

}
