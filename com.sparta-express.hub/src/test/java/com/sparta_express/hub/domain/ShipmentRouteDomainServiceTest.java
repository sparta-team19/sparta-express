package com.sparta_express.hub.domain;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.enumumerate.HubStatus;
import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentRouteDomainServiceTest {


    @Mock
    private HubRepository hubRepo; // Mock 객체

    @InjectMocks
    private ShipmentRouteDomainService service; // 의존성 주입된 서비스 클래스

    @Test
    void findShipmentRoutes() {
    }

    @Test
    void testFindShipmentInterhubRoutes() {


        // GIVEN
        Hub seoul = Hub.builder().id(UUID.fromString("03125302-ee89-417e-a043-cf9592c804a3")).status(HubStatus.ACTIVE).build();
        Hub gyeongi = Hub.builder().id(UUID.fromString("608a593a-eb94-4c39-b23d-d2d1d0e1d64b")).status(HubStatus.ACTIVE).build();
        Hub sejong = Hub.builder().id(UUID.fromString("b5a7694b-5177-471c-a821-5296f6a3c521")).status(HubStatus.ACTIVE).build();
        Hub daejon = Hub.builder().id(UUID.fromString("21b1768a-8395-49b1-bdf4-15c2b5962b5e")).status(HubStatus.ACTIVE).build();
        Hub busan = Hub.builder().id(UUID.fromString("5660247e-eef8-471c-a0c9-857c5c894b7c")).status(HubStatus.ACTIVE).build();
        List<Hub> hubList = List.of(seoul, gyeongi, sejong, daejon, busan);
        List<InterhubRoute> interhubRoutes = List.of(
                // 대전 - 서울
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(daejon)
                        .destinationHub(seoul)
                        .distanceKm(300)
                        .estimatedMinutes(180)
                        .build(),
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(seoul)
                        .destinationHub(daejon)
                        .distanceKm(300)
                        .estimatedMinutes(180)
                        .build(),
                // 부산 - 대전
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(daejon)
                        .destinationHub(busan)
                        .distanceKm(400)
                        .estimatedMinutes(240)
                        .build(),
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(busan)
                        .destinationHub(daejon)
                        .distanceKm(400)
                        .estimatedMinutes(240)
                        .build(),
                // 서울 - 세종
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(seoul)
                        .destinationHub(sejong)
                        .distanceKm(200)
                        .estimatedMinutes(120)
                        .build(),
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(sejong)
                        .destinationHub(seoul)
                        .distanceKm(200)
                        .estimatedMinutes(120)
                        .build(),
                // 경기 - 세종
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(gyeongi)
                        .destinationHub(seoul)
                        .distanceKm(200)
                        .estimatedMinutes(120)
                        .build(),
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(seoul)
                        .destinationHub(gyeongi)
                        .distanceKm(200)
                        .estimatedMinutes(120)
                        .build()
        );

        // Mock 설정
        when(hubRepo.readAllHubs()).thenReturn(hubList);
        when(hubRepo.readAllInterhubRoutes()).thenReturn(interhubRoutes);

        // WHEN: 서비스 호출
        List<InterhubRoute> result = service.findShipmentInterhubRoutes(busan.getId(), sejong.getId());
        List<InterhubRoute> expected = List.of(
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(busan)
                        .destinationHub(daejon)
                        .distanceKm(400)
                        .estimatedMinutes(240)
                        .build(),
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(daejon)
                        .destinationHub(seoul)
                        .distanceKm(300)
                        .estimatedMinutes(180)
                        .build(),
                InterhubRoute.builder()
                        .status(InterhubRouteStatus.ACTIVE)
                        .originHub(seoul)
                        .destinationHub(sejong)
                        .distanceKm(200)
                        .estimatedMinutes(120)
                        .build()
        );
        // THEN: 결과 검증
        for (int i=0; i<result.size(); i++) {
            InterhubRoute actual = result.get(i);
            InterhubRoute expect = expected.get(i);
            assert actual.getOriginHubId().equals(expect.getOriginHubId());
            assert actual.getDestinationHubId().equals(expect.getDestinationHubId());
            assert actual.getDistanceKm() == expect.getDistanceKm();
            assert actual.getEstimatedMinutes() == expect.getEstimatedMinutes();
        }

    }
}