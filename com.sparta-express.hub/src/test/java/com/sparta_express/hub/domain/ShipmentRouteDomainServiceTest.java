package com.sparta_express.hub.domain;

import com.sparta_express.hub.domain.model.FinalHubToDestination;
import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.ShipmentRoute;
import com.sparta_express.hub.domain.model.enumumerate.HubStatus;
import com.sparta_express.hub.domain.model.enumumerate.InterhubRouteStatus;
import com.sparta_express.hub.infrastructure.map.NaverMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ShipmentRouteDomainServiceTest {

    // GIVEN
    @Mock
    private HubRepository hubRepo; // Mock 객체

    @Mock
    private NaverMap naverMap; // Mock 객체

    @InjectMocks
    private ShipmentRouteDomainService service; // 의존성 주입된 서비스 클래스

    private Hub seoul = Hub.builder().id(UUID.fromString("03125302-ee89-417e-a043-cf9592c804a3"))
            .address("서울특별시 송파구 송파대로 55").latitude(37.474688).longitude(127.125170)
            .status(HubStatus.ACTIVE).build();
    private Hub gyeongi = Hub.builder().id(UUID.fromString("608a593a-eb94-4c39-b23d-d2d1d0e1d64b"))
            .address("경기도 고양시 덕양구 권율대로 570").latitude(37.640558).longitude(126.873834)
            .status(HubStatus.ACTIVE).build();
    private Hub sejong = Hub.builder().id(UUID.fromString("b5a7694b-5177-471c-a821-5296f6a3c521"))
            .address("세종특별자치시 한누리대로 2130").latitude(36.479999).longitude(127.289113)
            .status(HubStatus.ACTIVE).build();
    private Hub daejon = Hub.builder().id(UUID.fromString("21b1768a-8395-49b1-bdf4-15c2b5962b5e"))
            .address("대전 서구 둔산로 100").latitude(36.350244).longitude(127.384785)
            .status(HubStatus.ACTIVE).build();
    private Hub busan = Hub.builder().id(UUID.fromString("5660247e-eef8-471c-a0c9-857c5c894b7c"))
            .address("부산 동구 중앙대로 206").latitude(35.115837).longitude(129.042779)
            .status(HubStatus.ACTIVE).build();
    private List<Hub> hubList = List.of(seoul, gyeongi, sejong, daejon, busan);
    private List<InterhubRoute> givenInterhubRoutes = List.of(
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

    private List<InterhubRoute> expectedShipmentInterhubRoutes = List.of(
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

    @Test
    void findShipmentRoutes() {

        //given
        String address = "세종특별자치시 금남면 용포리 144-2";
        Position addressPosition
                = Position.builder().latitude(36.464739).longitude(127.281971).build();
        Position sejongPosition
                = Position.builder().latitude(sejong.getLatitude()).longitude(sejong.getLongitude()).build();
        HubToDestinationDTO hubToDestinationDTO
                = HubToDestinationDTO.builder().distanceKm(1).estimatedMinutes(3).build();


        when(hubRepo.readAllHubs()).thenReturn(hubList);
        when(hubRepo.readAllInterhubRoutes()).thenReturn(givenInterhubRoutes);

        when(naverMap.searchPosition(address)).thenReturn(addressPosition);
        when(naverMap.searchRoute(sejongPosition, addressPosition)).thenReturn(hubToDestinationDTO);


        ShipmentRoute shipmentRoutes
                = service.findShipmentRoutes(seoul.getId(), addressPosition);
        UUID expectedFinalHubId
                = expectedShipmentInterhubRoutes.get(expectedShipmentInterhubRoutes.size() - 1)
                .getDestinationHubId();
        FinalHubToDestination finalHubToDestination
                = shipmentRoutes.getFinalHubToDestination();

        assertThat(shipmentRoutes.getInterhubRoutes()).isEqualTo(expectedShipmentInterhubRoutes);
        assertThat(finalHubToDestination.getFinalHubId()).isEqualTo(expectedFinalHubId);
    }

    @Test
    void testFindShipmentInterhubRoutes() {

        // Mock 설정
        when(hubRepo.readAllHubs()).thenReturn(hubList);
        when(hubRepo.readAllInterhubRoutes()).thenReturn(givenInterhubRoutes);

        // WHEN: 서비스 호출
        List<InterhubRoute> result = service.findShipmentInterhubRoutes(busan.getId(), sejong.getId());

        // THEN: 결과 검증
        for (int i = 0; i < result.size(); i++) {
            InterhubRoute actual = result.get(i);
            InterhubRoute expect = expectedShipmentInterhubRoutes.get(i);
            assert actual.getOriginHubId().equals(expect.getOriginHubId());
            assert actual.getDestinationHubId().equals(expect.getDestinationHubId());
            assert actual.getDistanceKm() == expect.getDistanceKm();
            assert actual.getEstimatedMinutes() == expect.getEstimatedMinutes();
        }

    }
}