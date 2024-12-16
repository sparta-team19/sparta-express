package com.sparta_express.company_product.stock.repository;

import com.sparta_express.company_product.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {

    List<Stock> findAllByIsDeleteFalse();

    Optional<Stock> findByIdAndIsDeleteFalse(UUID id);

    Optional<Stock> findByProductIdAndIsDeleteFalse(UUID productId);
}
