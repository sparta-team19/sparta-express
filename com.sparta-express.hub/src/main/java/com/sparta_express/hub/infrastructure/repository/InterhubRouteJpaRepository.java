package com.sparta_express.hub.infrastructure.repository;

import com.sparta_express.hub.domain.model.InterhubRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterhubRouteJpaRepository extends JpaRepository<InterhubRoute, UUID> {
}
