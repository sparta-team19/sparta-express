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

import java.util.Set;
import java.util.UUID;

import static com.sparta_express.order_shipment.common.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentRouteService {

    private final ShipmentRouteRepository shipmentRouteRepository;
    private final UserClient userClient;
    private static final Set<String> ALLOWED_ROLES = Set.of("MASTER", "HUB_MANAGER", "DELIVERY_MANAGER");

    public ShipmentRouteResponse updateShipmentRoute(UUID shipmentRoutesId, ShipmentRouteUpdateDto updateDto, String email) {
        UserResponseDto userDto = userClient.getUserByEmail(email).getData();
        checkRole(userDto.getRole());
        ShipmentRoute shipmentRoute = checkShipmentRoute(shipmentRoutesId);
        checkUser(userDto.getEmail(), shipmentRoute);
        shipmentRoute.update(updateDto, email);
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

    private void checkRole(String role) {
        // MASTER, HUB_MANAGER, DELIVERY_MANAGER 만 가능
        if(!ALLOWED_ROLES.contains(role)) {
            throw new CustomException(COMMON_INVALID_ROLE);
        }
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
