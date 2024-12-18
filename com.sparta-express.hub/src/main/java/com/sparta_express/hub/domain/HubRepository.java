package com.sparta_express.hub.domain;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;

import java.util.List;


public interface HubRepository {
    List<Hub> readAllHubs();

    List<InterhubRoute> readAllInterhubRoutes();

    void saveAllHubs(List<Hub> hubs);

    void saveAllInterhubRoutes(List<InterhubRoute> interhubRoutes);
}
