package com.sparta_express.hub.domain;

import com.sparta_express.hub.domain.model.FinalHubToDestination;
import com.sparta_express.hub.domain.model.Hub;
import com.sparta_express.hub.domain.model.InterhubRoute;
import com.sparta_express.hub.domain.model.ShipmentRoute;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;


@Service
@RequiredArgsConstructor
public class ShipmentRouteDomainService {

    private final HubRepository hubRepo;
    private final MapApplication mapApplication;


    public final ShipmentRoute findShipmentRoutes(UUID originHubId,
                                                  Position destination) {

        List<Hub> hubList = hubRepo.readAllHubs();

        FinalHubToDestination finalHubToDestination
                = findFinalHubToDestination(hubList, destination);

        List<InterhubRoute> shipmentInterhubRoutes
                = findShipmentInterhubRoutes(
                originHubId, finalHubToDestination.getFinalHubId(), hubList
        );

        return ShipmentRoute.builder()
                .interhubRoutes(shipmentInterhubRoutes)
                .finalHubToDestination(finalHubToDestination)
                .build();
    }


    public final FinalHubToDestination findFinalHubToDestination(Position destinationPoint) {
        return findFinalHubToDestination(hubRepo.readAllHubs(), destinationPoint);
    }

    protected final FinalHubToDestination findFinalHubToDestination(List<Hub> hubList,
                                                                    Position destination) {

        assert (!hubList.isEmpty());

        Hub finalHub = findNearestHub(destination, hubList);

        HubToDestinationDTO route = mapApplication.searchRoute(finalHub.geometryPosition(), destination);

        return FinalHubToDestination.builder()
                .distanceKm(route.getDistanceKm())
                .estimatedMinutes(route.getEstimatedMinutes())
                .finalHubId(finalHub.getId())
                .build();
    }


    public final Hub findNearestHub(Position target) {
        return findNearestHub(target, hubRepo.readAllHubs());
    }

    protected final Hub findNearestHub(Position target, List<Hub> hubList) {

        assert (!hubList.isEmpty());

        Hub nearestHub = hubList.stream().min(
                comparingDouble(hub -> hub.geometryPosition().distance(target))
        ).orElseThrow();

        return nearestHub;
    }


    public final List<InterhubRoute> findShipmentInterhubRoutes(UUID originHubId,
                                                                UUID destinationHubId) {

        return findShipmentInterhubRoutes(originHubId, destinationHubId, hubRepo.readAllHubs());
    }

    protected final List<InterhubRoute> findShipmentInterhubRoutes(UUID originHubId,
                                                                   UUID destinationHubId,
                                                                   List<Hub> hubList) {

        assert (originHubId != destinationHubId);
        assert (hubList.stream().anyMatch(hub -> hub.getId().equals(originHubId)));
        assert (hubList.stream().anyMatch(hub -> hub.getId().equals(destinationHubId)));


        Map<UUID, List<InterhubRoute>> hubGraph
                = hubRepo.readAllInterhubRoutes().stream().collect(
                Collectors.groupingBy(InterhubRoute::getOriginHubId)
        );
        Map<UUID, HubRouteInfo> hubRouteMemo
                = hubList.stream().collect(Collectors.toMap(
                Hub::getId, HubRouteInfo::initMemo
        ));
        PriorityQueue<HubRouteInfo> hubRouteQue
                = new PriorityQueue<>(comparingInt(HubRouteInfo::getDistance));
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
                        = hubGraph.get(hubId).stream().map(nextInterhubRoute -> {
                            int nextDistance = newDistance + nextInterhubRoute.getDistanceKm();
                            UUID nextHubId = nextInterhubRoute.getDestinationHubId();
                            List<InterhubRoute> nextInterhubRoutes = new ArrayList<>(newRouteInfo.getInterhubRoutes());
                            nextInterhubRoutes.add(nextInterhubRoute);

                            return new HubRouteInfo(nextDistance, nextInterhubRoutes, nextHubId);
                        }
                ).toList();

                hubRouteQue.addAll(nextHubsToAdd);
            }
        }

        return hubRouteMemo.get(destinationHubId).interhubRoutes;
    }


    @Value
    private static class HubRouteInfo {

        int distance;
        List<InterhubRoute> interhubRoutes;
        UUID hubId;

        public static HubRouteInfo initMemo(Hub hub) {
            return new HubRouteInfo(Integer.MAX_VALUE, new ArrayList<>(), hub.getId());
        }

        public static HubRouteInfo initQueue(InterhubRoute routeFromOrigin) {
            return new HubRouteInfo(routeFromOrigin.getDistanceKm(), List.of(routeFromOrigin), routeFromOrigin.getDestinationHubId());
        }
    }
}