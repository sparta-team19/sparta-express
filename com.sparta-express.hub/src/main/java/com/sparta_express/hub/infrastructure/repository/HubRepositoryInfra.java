package com.sparta_express.hub.infrastructure.repository;

import com.sparta_express.hub.common.exception.CustomException;
import com.sparta_express.hub.common.exception.ErrorType;
import com.sparta_express.hub.domain.HubRepository;
import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
@Transactional
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


    public void deleteInterhubRoute(UUID interhubRouteId) {
        InterhubRoute toDelete = interhubRouteJpaRepo.findById(interhubRouteId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_RESOURCE));

        toDelete.setDelete(true);
        interhubRouteJpaRepo.save(toDelete);
    }

    public Hub readHub(UUID hubId) {

        return hubJpaRepo.findById(hubId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_RESOURCE));

    }

    public void deleteHub(UUID hubId) {
        Hub hubToDelete
                = hubJpaRepo.findById(hubId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_RESOURCE));
        hubToDelete.setDelete(true);
        hubJpaRepo.save(hubToDelete);

        List<InterhubRoute> interhubRoutesToDelete
                = interhubRouteJpaRepo.findByOriginHubOrDestinationHub(hubToDelete, hubToDelete);
        for (InterhubRoute interhubRoute : interhubRoutesToDelete) {
            interhubRoute.setDelete(true);
        }
        interhubRouteJpaRepo.saveAll(interhubRoutesToDelete);

    }
}
