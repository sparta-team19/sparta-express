package com.sparta_express.order_shipment.order.model;

import com.sparta_express.order_shipment.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private UUID requesterId;

    @Column(nullable = false)
    private UUID receiverId;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime dueDate;

    private String requestDetails;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Builder
    public Order(UUID productId, UUID requesterId, UUID receiverId,
                 Integer quantity, LocalDateTime dueDate, String requestDetails, OrderStatus orderStatus) {
        this.productId = productId;
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.requestDetails = requestDetails;
        this.orderStatus = orderStatus;
    }

    public static Order of(UUID productId, UUID requesterId, UUID receiverId,
                           Integer quantity, LocalDateTime dueDate, String requestDetails) {
        return Order.builder()
                .productId(productId)
                .requesterId(requesterId)
                .receiverId(receiverId)
                .quantity(quantity)
                .dueDate(dueDate)
                .requestDetails(requestDetails)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    public void update(Integer quantity, LocalDateTime dueDate, String requestDetails, OrderStatus orderStatus) {
        if (quantity != null) this.quantity = quantity;
        if (dueDate != null) this.dueDate = dueDate;
        if (requestDetails != null) this.requestDetails = requestDetails;
        if (orderStatus != null) this.orderStatus = orderStatus;
    }

}
