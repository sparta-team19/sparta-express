package com.sparta_express.order_shipment.domain.repository;

import com.sparta_express.order_shipment.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByIsDeleteFalse();

    Optional<Order> findByIdAndIsDeleteFalse(UUID id);

}
