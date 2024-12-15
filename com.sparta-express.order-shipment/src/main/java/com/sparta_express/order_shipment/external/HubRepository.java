package com.sparta_express.order_shipment.external;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubRepository extends JpaRepository<Hub, UUID> {
}
