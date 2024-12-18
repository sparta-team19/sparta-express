package com.sparta_express.hub.infrastructure.repository;

import com.sparta_express.hub.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//read only
public interface InterhubRouteJpaRepository extends JpaRepository<InterhubRoute, UUID> {
    Optional<InterhubRoute> findByIdAndIsDelete(UUID interhubRouteId, boolean b);

    List<InterhubRoute> findByOriginHubOrDestinationHub(Hub originHub, Hub destinationHub);
}
