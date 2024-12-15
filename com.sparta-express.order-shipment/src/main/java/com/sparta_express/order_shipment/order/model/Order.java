package com.sparta_express.company_product.external;

import com.sparta_express.order_shipment.common.BaseEntity;
import com.sparta_express.order_shipment.external.Product;
import com.sparta_express.order_shipment.external.User;
import com.sparta_express.order_shipment.order.model.OrderStatus;
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

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /*@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime dueDate;

    private String requestDetails;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Builder
    public Order(Product product, User requester, User receiver,
                 Integer quantity, LocalDateTime dueDate, String requestDetails, OrderStatus orderStatus) {
        this.product = product;
        this.requester = requester;
        this.receiver = receiver;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.requestDetails = requestDetails;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.PENDING; // 기본값 설정
    }

    public static Order of(Product product, User requester, User receiver,
                           Integer quantity, LocalDateTime dueDate, String requestDetails) {
        return Order.builder()
                .product(product)
                .requester(requester)
                .receiver(receiver)
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
