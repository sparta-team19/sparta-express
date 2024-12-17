package com.sparta_express.hub.infrastructure.repository;

import com.sparta_express.hub.common.exception.CustomException;
import com.sparta_express.hub.common.exception.ErrorType;
import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class HubRepositoryInfra implements HubRepository {

    private final HubJpaRepository hubJpaRepo;
    private final InterhubRouteJpaRepository interhubRouteJpaRepo;


    @Override
    public List<Hub> readAllHubs() {
        return hubJpaRepo.findAll();
    }

    @Override
    public List<InterhubRoute> readAllInterhubRoutes() {
        return interhubRouteJpaRepo.findAll();
    }


    @Override
    public void saveAllHubs(List<Hub> hubs) {
        hubJpaRepo.saveAll(hubs);
    }

    @Override
    public void saveAllInterhubRoutes(List<InterhubRoute> interhubRoutes) {
        interhubRouteJpaRepo.saveAll(interhubRoutes);
    }

    public InterhubRoute readInterhubRoute(UUID interhubRouteId) {

        return interhubRouteJpaRepo.findByIdAndIsDelete(interhubRouteId, false)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_RESOURCE));
    }
}
