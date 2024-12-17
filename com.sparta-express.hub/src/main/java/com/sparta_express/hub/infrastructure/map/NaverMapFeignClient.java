package com.sparta_express.hub.infrastructure.map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "naver-geocoding", url = "https://naveropenapi.apigw.ntruss.com")
public interface NaverMapFeignClient {

    @GetMapping("/map-geocode/v2/geocode")
    NaverGeocodingResponse searchCoordinates(@RequestHeader("x-ncp-apigw-api-key-id") String clientId,
                                             @RequestHeader("x-ncp-apigw-api-key") String clientSecret,
                                             @RequestHeader("Accept") String accept,
                                             @RequestParam("query") String address);

    @GetMapping("/map-direction/v1/driving")
    NaverDirection5Response searchRoute(@RequestHeader("x-ncp-apigw-api-key-id") String clientId,
                                        @RequestHeader("x-ncp-apigw-api-key") String clientSecret,
                                        @RequestHeader("Accept") String accept,
                                        @RequestParam("start") String start,
                                        @RequestParam("goal") String goal);

}