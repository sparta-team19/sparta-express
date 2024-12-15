package com.sparta_express.hub.infrastructure.map.naver_geocoding;

import com.sparta_express.hub.domain.Position;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class NaverGeocoding {

    private static final String ACCEPT_HEADER = "application/json";
    private final String clientId;
    private final String clientSecret;
    private final NaverGeocodingClient naverClient;

    public NaverGeocoding(@Value("${naver.api.client-id}") String clientId,
                          @Value("${naver.api.client-secret}") String clientSecret,
                          NaverGeocodingClient naverClient) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.naverClient = naverClient;
    }


    public Position searchGeometryPosition(String address) {

        NaverGeocodingResponse.Address searched
                = naverClient.searchCoordinates(clientId, clientSecret, ACCEPT_HEADER, address)
                .getAddresses().get(0);

        return Position.builder()
                .longitude(searched.getLongitude())
                .latitude(searched.getLatitude())
                .build();
    }


}
