package com.sparta_express.hub.presentation.controller;

import com.sparta_express.hub.domain.HubToDestinationDTO;
import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.infrastructure.map.MapInfra;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

//    private final MapApplication naverMap;
    private final MapInfra naverMap;

    @GetMapping("/geocoding")
    public Position geocodingTest() {
        String address = "포항시 송도동 418-14";
        Position position = naverMap.searchPosition(address);

        return position;
    }

    @GetMapping("/direction5")
    public HubToDestinationDTO direction5Test() {

        Position start = Position.builder().latitude(36.036453).longitude(129.375581).build(); //"포항시 송도동 418-14";
        Position goal = Position.builder().latitude(35.856675).longitude(129.225107).build(); //"경주 시청";

        HubToDestinationDTO hubToDestinationDTO = naverMap.searchRoute(start, goal);

        return hubToDestinationDTO;
    }
}
