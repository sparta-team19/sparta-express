package com.sparta_express.hub.infrastructure.map.naver_geocoding;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "naver-geocoding", url = "https://naveropenapi.apigw.ntruss.com")
public interface NaverGeocodingClient {

    @GetMapping("/map-geocode/v2/geocode")
    NaverGeocodingResponse searchCoordinates(@RequestHeader("x-ncp-apigw-api-key-id") String clientId,
                                             @RequestHeader("x-ncp-apigw-api-key") String clientSecret,
                                             @RequestHeader("Accept") String accept,
                                             @RequestParam("query") String address);
}