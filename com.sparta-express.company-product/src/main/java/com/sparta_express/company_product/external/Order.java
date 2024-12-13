package com.sparta_express.company_product.external;

import com.sparta_express.company_product.common.BaseEntity;
import com.sparta_express.company_product.product.model.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @Column(nullable = false)
    private UUID requesterId;

    @Column(nullable = false)
    private UUID receiverId;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime dueDate;

    private String requestDetails;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
