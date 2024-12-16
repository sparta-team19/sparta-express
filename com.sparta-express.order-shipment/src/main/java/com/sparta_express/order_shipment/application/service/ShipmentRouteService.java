package com.sparta_express.order_shipment.application.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.sparta_express.order_shipment.application.dto.ShipmentRouteResponse;
import com.sparta_express.order_shipment.application.dto.ShipmentRouteUpdateDto;
import com.sparta_express.order_shipment.domain.entity.QShipmentRoute;
import com.sparta_express.order_shipment.domain.entity.ShipmentRoute;
import com.sparta_express.order_shipment.common.exception.CustomException;
import com.sparta_express.order_shipment.domain.repository.ShipmentRouteRepository;
import com.sparta_express.order_shipment.infrastructure.client.UserClient;
import com.sparta_express.order_shipment.infrastructure.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sparta_express.order_shipment.common.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentRouteService {

    private final ShipmentRouteRepository shipmentRouteRepository;
    private final UserClient userClient;

    public ShipmentRouteResponse updateShipmentRoute(UUID shipmentRoutesId, ShipmentRouteUpdateDto updateDto, String userId) {
        /* todo:
            1. 권한 체크 로직 추가 - checkRole 메소드 내 구현
            feignclient 로 유저 조회 한 뒤 권한 체크
            MASTER, HUB_MANAGER, DELIVERY_MANAGER 만 가능
         */
        UserResponseDto userDto = userClient.getUser(userId).getData();
        checkRole(userDto.getEmail());
        ShipmentRoute shipmentRoute = checkShipmentRoute(shipmentRoutesId);
        checkUser(userDto.getEmail(), shipmentRoute);
        shipmentRoute.update(updateDto, userId);
        return ShipmentRouteResponse.from(shipmentRoute);
    }

    @Transactional(readOnly = true)
    public Page<ShipmentRouteResponse> getShipmentRoutesList(Predicate predicate, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder(predicate);
        builder.and(QShipmentRoute.shipmentRoute.isDelete.eq(false));
        Page<ShipmentRoute> shipmentRoutePage = shipmentRouteRepository.findAll(builder, pageable);
        return shipmentRoutePage.map(ShipmentRouteResponse::from);
    }


    @Transactional(readOnly = true)
    public ShipmentRouteResponse getShipmentRoute(UUID shipmentRoutesId) {
        ShipmentRoute shipmentRoute = checkShipmentRoute(shipmentRoutesId);
        return ShipmentRouteResponse.from(shipmentRoute);
    }

    public void deleteShipmentRoute(UUID shipmentRoutesId, String userId) {
        checkRole(userId);
        ShipmentRoute shipmentRoute = checkShipmentRoute(shipmentRoutesId);
        checkUser(userId, shipmentRoute);
        shipmentRoute.delete(userId);
    }

    private void checkRole(String userId) {
        // todo: 권한체크

    }

    private ShipmentRoute checkShipmentRoute(UUID shipmentRoutesId) {
        return shipmentRouteRepository.findByIdAndIsDeleteFalse(shipmentRoutesId)
                .orElseThrow(() -> new CustomException(SHIPMENT_ROUTE_NOT_FOUND));
    }

    private void checkUser(String userId, ShipmentRoute shipmentRoute) {
        if(!userId.equals(shipmentRoute.getUserId())) {
            throw new CustomException(USER_NOT_SAME);
        }
    }

}
