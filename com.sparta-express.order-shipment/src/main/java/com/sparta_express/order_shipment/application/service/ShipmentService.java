package com.sparta_express.order_shipment.application.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.sparta_express.order_shipment.application.dto.ShipmentResponse;
import com.sparta_express.order_shipment.application.dto.ShipmentUpdateDto;
import com.sparta_express.order_shipment.domain.entity.QShipment;
import com.sparta_express.order_shipment.domain.entity.Shipment;
import com.sparta_express.order_shipment.domain.entity.ShipmentRoute;
import com.sparta_express.order_shipment.common.exception.CustomException;
import com.sparta_express.order_shipment.domain.repository.ShipmentRepository;
import com.sparta_express.order_shipment.infrastructure.client.UserClient;
import com.sparta_express.order_shipment.infrastructure.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.sparta_express.order_shipment.common.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final UserClient userClient;
    private static final Set<String> ALLOWED_ROLES = Set.of("MASTER", "HUB_MANAGER", "DELIVERY_MANAGER");

    public ShipmentResponse updateShipment(UUID shipmentId, ShipmentUpdateDto updateDto, String email) {
        UserResponseDto userDto = userClient.getUserByEmail(email).getData();
        checkRole(userDto.getRole());
        Shipment shipment = checkShipment(shipmentId);
        checkUser(userDto.getEmail(), shipment);
        shipment.update(updateDto, email);
        return ShipmentResponse.from(shipment);
    }

    @Transactional(readOnly = true)
    public Page<ShipmentResponse> getShipmentsList(Predicate predicate, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder(predicate);
        builder.and(QShipment.shipment.isDelete.eq(false));
        Page<Shipment> shipmentPage = shipmentRepository.findAll(builder, pageable);
        return shipmentPage.map(ShipmentResponse::from);
    }

    @Transactional(readOnly = true)
    public ShipmentResponse getShipment(UUID shipmentId) {
        Shipment shipment = checkShipment(shipmentId);
        return ShipmentResponse.from(shipment);
    }

    public void deleteShipment(UUID shipmentId, String userId) {
        checkRole(userId);
        Shipment shipment = checkShipment(shipmentId);
        checkUser(userId, shipment);
        shipment.delete(userId);
        List<ShipmentRoute> shipmentRouteList = shipment.getShipmentRoutes();
        shipmentRouteList.forEach(shipmentRoute -> {shipmentRoute.delete(userId);});
    }

    private void checkRole(String role) {
        // MASTER, HUB_MANAGER, DELIVERY_MANAGER 만 가능
        if(!ALLOWED_ROLES.contains(role)) {
            throw new CustomException(COMMON_INVALID_ROLE);
        }
    }

    private Shipment checkShipment(UUID shipmentId) {
        return shipmentRepository.findByIdAndIsDeleteFalse(shipmentId)
                .orElseThrow(() -> new CustomException(SHIPMENT_NOT_FOUND));
    }

    private void checkUser(String userId, Shipment shipment) {
        if(!userId.equals(shipment.getUserId())) {
            throw new CustomException(USER_NOT_SAME);
        }
    }

}
