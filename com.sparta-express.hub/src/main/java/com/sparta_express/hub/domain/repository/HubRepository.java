package com.sparta_express.hub.domain.repository;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HubRepository {
    List<Hub> readAllHubs();

    List<InterhubRoute> readAllInterhubRoutes();
}
