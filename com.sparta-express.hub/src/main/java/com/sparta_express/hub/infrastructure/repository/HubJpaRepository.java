package com.sparta_express.hub.infrastructure.repository;

import com.sparta_express.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubJpaRepository extends JpaRepository<Hub, UUID> {
}
