package com.sparta_express.auth.user.repository;

import com.sparta_express.auth.user.entity.DeliveryManager;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, UUID> {

    Page<DeliveryManager> findAllByUserId(Long id, Pageable pageable);

    Optional<DeliveryManager> findByUserId(Long id);

    Integer findMaxDeliverySequence();
}
