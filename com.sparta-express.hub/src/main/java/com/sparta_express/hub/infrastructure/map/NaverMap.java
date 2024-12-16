package com.sparta_express.hub.infrastructure.map;

import com.sparta_express.hub.domain.HubToDestinationDTO;
import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.infrastructure.map.naver_geocoding.NaverGeocoding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NaverMap implements MapInfra {

    private final NaverGeocoding naverGeocoding;

    @Override
    public HubToDestinationDTO searchRoute(Position origin, Position destination) {
        return null;
    }

    @Override
    public Position searchGeometryPoint(String address) {
        return naverGeocoding.searchGeometryPosition(address);
    }
}
