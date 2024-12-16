package com.sparta_express.hub.infrastructure.repository;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.HubRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class HubRepositoryInfra implements HubRepository {
    @Override
    public List<Hub> readAllHubs() {
        return null;
    }

    @Override
    public List<InterhubRoute> readAllInterhubRoutes() {
        return null;
    }
}
