package com.sparta_express.hub.infrastructure.map;

import com.sparta_express.hub.common.exception.CustomException;
import com.sparta_express.hub.common.exception.ErrorType;
import com.sparta_express.hub.domain.HubToDestinationDTO;
import com.sparta_express.hub.domain.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NaverMap implements MapApiInfra {

    private static final String ACCEPT_HEADER = "application/json";
    private final String clientId;
    private final String clientSecret;
    private final NaverMapFeignClient feignClient;

    public NaverMap(@Value("${naver.api.client.id}") String clientId,
                    @Value("${naver.api.client.secret}") String clientSecret,
                    NaverMapFeignClient feignClient) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.feignClient = feignClient;
    }

    @Override
    public HubToDestinationDTO searchRoute(Position origin, Position destination) {

        String start = String.format("%f,%f", origin.getLongitude(), origin.getLatitude());
        String goal = String.format("%f,%f", destination.getLongitude(), destination.getLatitude());

        NaverDirection5Response response
                = feignClient.searchRoute(clientId, clientSecret, ACCEPT_HEADER, start, goal);

        if (response.isFailed()) {
            log.info("naver direction 5 failed: code = " + response.getCode());
            throw new CustomException(ErrorType.MAP_API_ERROR);
        }

        return HubToDestinationDTO.builder()
                .distanceKm(
                        (int) Math.round(response.getDistanceMeter() / 1000.0)
                )
                .estimatedMinutes(
                        (int) Math.round(response.getDurationMilliSeconds() / (1000.0 * 60.0))
                )
                .build();
    }

    @Override
    public Position searchPosition(String address) {

        NaverGeocodingResponse response
                = feignClient.searchCoordinates(clientId, clientSecret, ACCEPT_HEADER, address);

        if (response.isFailed()) {
            log.info("naver geocoding failed: status = " + response.getStatus());
            throw new CustomException(ErrorType.MAP_API_ERROR);
        }

        NaverGeocodingResponse.Address searched
                = response.getAddresses().get(0);

        return Position.builder()
                .longitude(searched.getLongitude())
                .latitude(searched.getLatitude())
                .build();
    }
}