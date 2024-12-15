package com.sparta_express.order_shipment.external;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByIsDeleteFalse();

    Optional<Product> findByIdAndIsDeleteFalse(UUID id);

}
