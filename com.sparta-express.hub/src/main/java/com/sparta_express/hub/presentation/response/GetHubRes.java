package com.sparta_express.hub.presentation.response;


import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.enumumerate.HubStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class GetHubRes {

    UUID id;
    String address;
    HubStatus status;
    double latitude;
    double longitude;

    public static GetHubRes from(Hub hub) {

        return GetHubRes.builder()
                .id(hub.getId())
                .address(hub.getAddress())
                .status(hub.getStatus())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .build();
    }
}
