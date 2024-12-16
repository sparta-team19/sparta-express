package com.sparta_express.auth.user.repository;

import com.querydsl.core.types.Predicate;
import com.sparta_express.auth.user.entity.DeliveryManager;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, UUID>,
    QuerydslPredicateExecutor<DeliveryManager> {

    Page<DeliveryManager> findAllByUserId(Long id, Pageable pageable);

    Optional<DeliveryManager> findByUserId(Long id);

    @Query("SELECT MAX(d.deliverySequence) FROM DeliveryManager d")
    Integer findMaxDeliverySequence();

    Page<DeliveryManager> findAll(Predicate predicate, Pageable pageable);
}
