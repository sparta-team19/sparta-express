package com.sparta_express.hub.infrastructure.repository;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.repository.HubRepository;

import java.util.List;

public class HubRepositoryImpl implements HubRepository {
    @Override
    public List<Hub> readAllHubs() {
        return null;
    }

    @Override
    public List<InterhubRoute> readAllInterhubRoutes() {
        return null;
    }
}
