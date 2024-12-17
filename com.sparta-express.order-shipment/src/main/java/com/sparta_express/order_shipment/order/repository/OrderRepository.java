package com.sparta_express.order_shipment.order.repository;

import com.sparta_express.order_shipment.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByIsDeleteFalse();

    Optional<Order> findByIdAndIsDeleteFalse(UUID id);

}