package com.sparta_express.hub.presentation.controller;

import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.infrastructure.map.naver_geocoding.NaverGeocoding;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final NaverGeocoding naverGeocoding;

    @GetMapping("/test")
    public Position geocodingTest() {
        String address = "포항시 송도동 418-14";
        Position position = naverGeocoding.searchPosition(address);

        return position;

    }
}
