package com.sparta_express.hub.domain.service;

import com.sparta_express.hub.domain.model.FinalHubToDestination;
import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.ShipmentRoute;
import com.sparta_express.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ShipmentRouteDomainService {

    private final HubRepository hubRepo;

    public final ShipmentRoute findShipmentRoutes(UUID originHubId,
                                                  String destinationAddress) {

        List<Hub> hubList = hubRepo.readAllHubs();

        FinalHubToDestination finalHubToDestination
                = findFinalHubToDestination(hubList, destinationAddress);

        List<InterhubRoute> shipmentInterhubRoutes
                = findShipmentInterhubRoutes(originHubId, finalHubToDestination.getFinalHubId(), hubList);

        return ShipmentRoute.builder()
                .interhubRoutes(shipmentInterhubRoutes)
                .finalHubToDestination(finalHubToDestination)
                .build();
    }


    public final FinalHubToDestination findFinalHubToDestination(String destinationAddress) {
        return findFinalHubToDestination(hubRepo.readAllHubs(), destinationAddress);
    }

    protected final FinalHubToDestination findFinalHubToDestination(List<Hub> hubList,
                                                                    String destinationAddress) {

        assert (!hubList.isEmpty() && !destinationAddress.isBlank());

        // FinalHub - destinationAddress 간 거리, 시간 탐색 및 저장

        //findShipmentRouteFromNearestHub(String destinationAddress, List<Hub> hubList
        // 마지막 허브, 마지막허브에서 목적지까지의 거리, 시간 계산, 외부 api domain에 dip 추상화 시켜야함
        UUID destinationHubId = findNearestHubId(destinationAddress, hubList);

        return FinalHubToDestination.builder()
                .distanceKm()
                .estimatedMinutes()
                .finalHubId()
                .build();
    }


    public final UUID findNearestHubId(String destinationAddress) {
        return findNearestHubId(destinationAddress, hubRepo.readAllHubs());
    }

    protected final UUID findNearestHubId(String destinationAddress, List<Hub> hubList) {

        return null;
    }


    public final List<InterhubRoute> findShipmentInterhubRoutes(UUID originHubId,
                                                                UUID destinationHubId) {

        return findShipmentInterhubRoutes(originHubId, destinationHubId, hubRepo.readAllHubs());
    }
    protected final List<InterhubRoute> findShipmentInterhubRoutes(UUID originHubId,
                                                                UUID destinationHubId,
                                                                List<Hub> hubList) {

        assert (originHubId != destinationHubId
                && hubList.stream().anyMatch(hub -> hub.getId().equals(originHubId))
                && hubList.stream().anyMatch(hub -> hub.getId().equals(destinationHubId)));

        Map<UUID, List<InterhubRoute>> hubGraph
                = hubRepo.readAllInterhubRoutes().stream().collect(
                Collectors.groupingBy(InterhubRoute::getOriginHubId)
        );
        Map<UUID, HubRouteInfo> hubRouteMemo
                = hubList.stream().collect(Collectors.toMap(
                Hub::getId, HubRouteInfo::initMemo
        ));
        PriorityQueue<HubRouteInfo> hubRouteQue
                = new PriorityQueue<>(Comparator.comparingInt(HubRouteInfo::getDistance));
        hubRouteQue.addAll(hubGraph.get(originHubId).stream()
                .map(HubRouteInfo::initQueue).toList()
        );

        while (!hubRouteQue.isEmpty()) {
            HubRouteInfo newRouteInfo = hubRouteQue.poll();
            int newDistance = newRouteInfo.getDistance();
            UUID hubId = newRouteInfo.getHubId();

            if (newDistance < hubRouteMemo.get(hubId).getDistance()) {
                hubRouteMemo.put(hubId, newRouteInfo);

                List<HubRouteInfo> nextHubsToAdd
                        = hubGraph.get(hubId).stream().map(
                        nextInterhubRoute -> {
                            int nextDistance = newDistance + nextInterhubRoute.getDistanceKm();
                            UUID nextHubId = nextInterhubRoute.getDestinationHubId();
                            List<InterhubRoute> nextInterhubRoutes = newRouteInfo.getInterhubRoutes();
                            nextInterhubRoutes.add(nextInterhubRoute);

                            return new HubRouteInfo(nextDistance, nextInterhubRoutes, nextHubId);
                        }
                ).toList();
                hubRouteQue.addAll(nextHubsToAdd);
            }
        }

        return hubRouteMemo.get(destinationHubId).getInterhubRoutes();
    }

}
