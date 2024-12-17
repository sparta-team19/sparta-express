package com.sparta_express.hub.application;


import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.infrastructure.repository.HubRepositoryInfra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterhubRouteService {

    private final HubRepositoryInfra repo;

    public InterhubRoute readInterhubRoute(UUID interhubRouteId) {

        return repo.readInterhubRoute(interhubRouteId);
    }
}
