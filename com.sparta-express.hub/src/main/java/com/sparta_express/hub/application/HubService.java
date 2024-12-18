package com.sparta_express.hub.application;


import com.sparta_express.hub.Hub;
import com.sparta_express.hub.infrastructure.repository.HubRepositoryInfra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepositoryInfra hubRepo;

    public Hub readHub(UUID hubId) {

        return hubRepo.readHub(hubId);
    }

    public void deleteHub(UUID hubId) {
        hubRepo.deleteHub(hubId);
    }
}
