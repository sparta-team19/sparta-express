package com.sparta_express.hub;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.enumumerate.HubStatus;
import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;

import java.util.List;
import java.util.UUID;

public class ShipmentInterhubRoutesTestCase {


    //test case
    // 부산 -> 세종 : 부산 -> 대전 -> 서울 -> 세종
    public final Hub seoul = Hub.builder().id(UUID.fromString("03125302-ee89-417e-a043-cf9592c804a3"))
            .address("서울특별시 송파구 송파대로 55").latitude(37.474688).longitude(127.125170)
            .status(HubStatus.ACTIVE).build();
    public final Hub gyeongi = Hub.builder().id(UUID.fromString("608a593a-eb94-4c39-b23d-d2d1d0e1d64b"))
            .address("경기도 고양시 덕양구 권율대로 570").latitude(37.640558).longitude(126.873834)
            .status(HubStatus.ACTIVE).build();
    public final Hub sejong = Hub.builder().id(UUID.fromString("b5a7694b-5177-471c-a821-5296f6a3c521"))
            .address("세종특별자치시 한누리대로 2130").latitude(36.479999).longitude(127.289113)
            .status(HubStatus.ACTIVE).build();
    public final Hub daejon = Hub.builder().id(UUID.fromString("21b1768a-8395-49b1-bdf4-15c2b5962b5e"))
            .address("대전 서구 둔산로 100").latitude(36.350244).longitude(127.384785)
            .status(HubStatus.ACTIVE).build();
    public final Hub busan = Hub.builder().id(UUID.fromString("5660247e-eef8-471c-a0c9-857c5c894b7c"))
            .address("부산 동구 중앙대로 206").latitude(35.115837).longitude(129.042779)
            .status(HubStatus.ACTIVE).build();
    public final List<Hub> hubList = List.of(seoul, gyeongi, sejong, daejon, busan);
    public final List<InterhubRoute> givenInterhubRoutes = List.of(
            // 대전 - 서울
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(daejon).destinationHub(seoul)
                    .distanceKm(300).estimatedMinutes(180).build(),
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(seoul).destinationHub(daejon)
                    .distanceKm(300).estimatedMinutes(180)
                    .build(),
            // 부산 - 대전
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(daejon).destinationHub(busan)
                    .distanceKm(400).estimatedMinutes(240)
                    .build(),
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(busan).destinationHub(daejon)
                    .distanceKm(400).estimatedMinutes(240)
                    .build(),
            // 서울 - 세종
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(seoul).destinationHub(sejong)
                    .distanceKm(200).estimatedMinutes(120)
                    .build(),
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(sejong).destinationHub(seoul)
                    .distanceKm(200).estimatedMinutes(120)
                    .build(),
            // 경기 - 세종
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(gyeongi).destinationHub(seoul)
                    .distanceKm(200).estimatedMinutes(120)
                    .build(),
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(seoul).destinationHub(gyeongi)
                    .distanceKm(200).estimatedMinutes(120)
                    .build()
    );

    public final List<InterhubRoute> expectedShipmentInterhubRoutes = List.of(
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(busan).destinationHub(daejon)
                    .distanceKm(400).estimatedMinutes(240)
                    .build(),
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(daejon).destinationHub(seoul)
                    .distanceKm(300).estimatedMinutes(180)
                    .build(),
            InterhubRoute.builder().status(InterhubRouteStatus.ACTIVE)
                    .originHub(seoul).destinationHub(sejong)
                    .distanceKm(200).estimatedMinutes(120)
                    .build()
    );

}
