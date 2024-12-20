package com.sparta_express.hub.presentation.controller;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.HubToDestinationDTO;
import com.sparta_express.hub.domain.Position;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.enumumerate.HubStatus;
import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;
import com.sparta_express.hub.infrastructure.map.MapApiInfra;
import com.sparta_express.hub.infrastructure.repository.HubJpaRepository;
import com.sparta_express.hub.infrastructure.repository.InterhubRouteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SuppressWarnings("unused")

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    //    private final MapApplication naverMap;
    private final MapApiInfra naverMap;
    private final HubJpaRepository hubJpaRepo;
    private final InterhubRouteJpaRepository interhubRouteJpaRepo;


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

    //repo test
    private Hub seoul = Hub.builder()
            .address("서울특별시 송파구 송파대로 55").latitude(37.474688).longitude(127.125170)
            .status(HubStatus.ACTIVE).build();
    private Hub gyeongi = Hub.builder()
            .address("경기도 고양시 덕양구 권율대로 570").latitude(37.640558).longitude(126.873834)
            .status(HubStatus.ACTIVE).build();
    private Hub sejong = Hub.builder()
            .address("세종특별자치시 한누리대로 2130").latitude(36.479999).longitude(127.289113)
            .status(HubStatus.ACTIVE).build();
    private Hub daejon = Hub.builder()
            .address("대전 서구 둔산로 100").latitude(36.350244).longitude(127.384785)
            .status(HubStatus.ACTIVE).build();
    private Hub busan = Hub.builder()
            .address("부산 동구 중앙대로 206").latitude(35.115837).longitude(129.042779)
            .status(HubStatus.ACTIVE).build();
    private List<Hub> hubList = List.of(seoul, gyeongi, sejong, daejon, busan);


    @GetMapping("/hubRepo/save")
    public String hubRepoSaveTest() {
        hubJpaRepo.saveAll(hubList);
        return "success";
    }

    @GetMapping("/hubRepo/read")
    public List<Hub> hubRepoTest() {
        hubJpaRepo.saveAll(hubList);
        return hubJpaRepo.findAll();
    }


    @GetMapping("/interhubRepo/save")
    public String interhubRouteRepoSaveTest() {
        Hub seoul = hubJpaRepo.findByAddress(this.seoul.getAddress());
        Hub gyeongi = hubJpaRepo.findByAddress(this.gyeongi.getAddress());
        Hub sejong = hubJpaRepo.findByAddress(this.sejong.getAddress());
        Hub daejon = hubJpaRepo.findByAddress(this.daejon.getAddress());
        Hub busan = hubJpaRepo.findByAddress(this.busan.getAddress());
        List<InterhubRoute> givenInterhubRoutes = List.of(
                // 대전 - 서울
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(daejon).destinationHub(seoul)
                        .distanceKm(300).estimatedMinutes(180).build(),
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(seoul).destinationHub(daejon)
                        .distanceKm(300).estimatedMinutes(180).build(),
                // 부산 - 대전
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(daejon).destinationHub(busan)
                        .distanceKm(400).estimatedMinutes(240).build(),
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(busan).destinationHub(daejon)
                        .distanceKm(400).estimatedMinutes(240).build(),
                // 서울 - 세종
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(seoul).destinationHub(sejong)
                        .distanceKm(200).estimatedMinutes(120).build(),
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(sejong).destinationHub(seoul)
                        .distanceKm(200).estimatedMinutes(120).build(),
                // 경기 - 세종
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(gyeongi).destinationHub(seoul)
                        .distanceKm(200).estimatedMinutes(120).build(),
                InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE).originHub(seoul).destinationHub(gyeongi)
                        .distanceKm(200).estimatedMinutes(120).build()
        );
        interhubRouteJpaRepo.saveAll(givenInterhubRoutes);
        return "success";
    }

    @GetMapping("/interhubRepo/read")
    public List<InterhubRoute> interhubRouteRepoTest() {
        return interhubRouteJpaRepo.findAll();
    }
}