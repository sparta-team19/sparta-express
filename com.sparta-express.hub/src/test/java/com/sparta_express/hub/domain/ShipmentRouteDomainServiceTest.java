package com.sparta_express.hub.domain;

import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.ShipmentInterhubRoutesTestCase;
import com.sparta_express.hub.domain.model.LastHubToDestination;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.ShipmentRoute;
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
    private ShipmentInterhubRoutesTestCase testCase = new ShipmentInterhubRoutesTestCase();
    @Mock
    public HubRepository hubRepo; // Mock 객체
    @Mock
    private NaverMap naverMap; // Mock 객체
    @InjectMocks
    private ShipmentRouteDomainService service; // 의존성 주입된 서비스 클래스


    @Test
    void findShipmentRoutes() {

        //given
        Hub sejong = testCase.sejong, seoul = testCase.seoul;
        List<Hub> hubList = testCase.hubList;
        List<InterhubRoute> givenInterhubRoutes = testCase.givenInterhubRoutes;
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


        List<InterhubRoute> expectedShipmentInterhubRoutes
                = testCase.expectedShipmentInterhubRoutes;
        ShipmentRoute resultShipmentRoutes
                = service.findShipmentRoutes(seoul.getId(), addressPosition);
        UUID expectedFinalHubId
                = expectedShipmentInterhubRoutes.get(expectedShipmentInterhubRoutes.size() - 1)
                .getDestinationHubId();
        LastHubToDestination lastHubToDestination
                = resultShipmentRoutes.getLastHubToDestination();

        assertThat(resultShipmentRoutes.getInterhubRoutes()).isEqualTo(expectedShipmentInterhubRoutes);
        assertThat(lastHubToDestination.getLastHubId()).isEqualTo(expectedFinalHubId);
    }

    @Test
    void findShipmentInterhubRoutes() {

        //given
        List<Hub> hubList = testCase.hubList;
        List<InterhubRoute> givenInterhubRoutes = testCase.givenInterhubRoutes;
        Hub busan = testCase.busan, sejong = testCase.sejong;

        when(hubRepo.readAllHubs()).thenReturn(hubList);
        when(hubRepo.readAllInterhubRoutes()).thenReturn(givenInterhubRoutes);

        // THEN: 결과 검증
        List<InterhubRoute> result = service.findShipmentInterhubRoutes(busan.getId(), sejong.getId());
        List<InterhubRoute> expectedShipmentInterhubRoutes = testCase.expectedShipmentInterhubRoutes;

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