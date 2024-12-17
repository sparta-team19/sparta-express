package com.sparta_express.order_shipment.application.service;

import com.sparta_express.order_shipment.application.dto.OrderCreateResponse;
import com.sparta_express.order_shipment.application.dto.OrderDto;
import com.sparta_express.order_shipment.common.exception.CustomException;
import com.sparta_express.order_shipment.common.exception.ErrorType;
import com.sparta_express.order_shipment.domain.entity.Order;
import com.sparta_express.order_shipment.domain.entity.Shipment;
import com.sparta_express.order_shipment.domain.entity.ShipmentRoute;
import com.sparta_express.order_shipment.domain.repository.OrderRepository;
import com.sparta_express.order_shipment.domain.repository.ShipmentRouteRepository;
import com.sparta_express.order_shipment.infrastructure.client.CompanyProductClient;
import com.sparta_express.order_shipment.infrastructure.client.HubClient;
import com.sparta_express.order_shipment.infrastructure.client.UserClient;
import com.sparta_express.order_shipment.infrastructure.dto.*;
import com.sparta_express.order_shipment.presentation.dto.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShipmentRouteRepository shipmentRouteRepository;
    private final CompanyProductClient companyProductClient;
    private final HubClient hubClient;
    private final UserClient userClient;

    public OrderCreateResponse createOrder(OrderDto request, String email) {

        boolean isProductStockDecreased = false;

        try {
            UserResponseDto userResponse = userClient.getUserByEmail(email).getData();
            log.info("### Fetching userResponse details : {}", userResponse.toString());
            CompanyResponseDto requesterResponse = companyProductClient.getCompanyById(request.getRequesterId()).getData();
            log.info("### Fetching requesterResponse details : {}", requesterResponse.toString());
            CompanyResponseDto receiverResponse = companyProductClient.getCompanyById(request.getReceiverId()).getData();
            log.info("### Fetching receiverResponse details : {}", receiverResponse.toString());
            ProductResponseDto productResponse = companyProductClient.getProductById(request.getProductId()).getData();
            log.info("### Fetching productResponse details : {}", productResponse.toString());
            companyProductClient.reduceStock(productResponse.getId(), request.getQuantity());
            isProductStockDecreased = true;
            List<HubResponseDto> hubResponseList = hubClient.getHubRoutesInfo(requesterResponse.getHubId(), receiverResponse.getHubId()).getData();
            log.info("### Fetching productResponse details : {}", hubResponseList.toString());

            Order order = buildOrder(requesterResponse, receiverResponse, productResponse, userResponse, request);
            Shipment shipment = buildShipment(requesterResponse, receiverResponse, userResponse, request, order);
            order.updateShipment(shipment);
            orderRepository.save(order);
            shipmentRouteRepository.saveAll(createShipmentRoutes(hubResponseList, shipment, request, email));

            return OrderCreateResponse.from(order);

        } catch (Exception e) {
            if (isProductStockDecreased) {
                companyProductClient.restoreStock(request.getProductId(), request.getQuantity());
            }
            log.error(e.getMessage(), e);
            throw new RuntimeException("Error creating order", e);
        }

    }

    // 주문 목록 조회
    public List<Order> getOrders() {
        return orderRepository.findAllByIsDeleteFalse();
    }

    // 주문 상세 조회
    public Order getOrderById(UUID id) {
        return orderRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.ORDER_NOT_FOUND));
    }

    // 주문 수정
    public Order updateOrder(UUID id, UpdateOrderRequest request) {
        Order order = orderRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.ORDER_NOT_FOUND));
        order.update(request.getQuantity(), request.getDueDate(), request.getRequestDetails(), request.getOrderStatus());
        return order;
    }

    // 주문 삭제
    @Transactional
    public void deleteOrder(UUID id, String email) {
        Order order = orderRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.ORDER_NOT_FOUND));
        order.delete(email);
    }

    private Order buildOrder(CompanyResponseDto requesterResponse,
                             CompanyResponseDto receiverResponse,
                             ProductResponseDto productResponse,
                             UserResponseDto userResponse,
                             OrderDto request) {
        return Order.of(
                requesterResponse.getId(),
                receiverResponse.getId(),
                productResponse.getId(),
                request.getQuantity(),
                request.getDueDate(),
                request.getRequestDetails(),
                userResponse.getEmail()
        );
    }

    private Shipment buildShipment(CompanyResponseDto requesterResponse,
                                   CompanyResponseDto receiverResponse,
                                   UserResponseDto userResponse,
                                   OrderDto request, Order order) {
        return Shipment.of(
                requesterResponse.getHubId(),
                receiverResponse.getHubId(),
                request.getDeliveryAddress(),
                request.getRecipientName(),
                request.getRecipientSlackId(),
                order,
                userResponse.getEmail()
        );
    }

    private List<ShipmentRoute> createShipmentRoutes(List<HubResponseDto> hubResponseList,
                                                     Shipment shipment,
                                                     OrderDto request,
                                                     String userId) {
        return IntStream.range(0, hubResponseList.size())
                .mapToObj(i -> buildShipmentRoute(hubResponseList.get(i), i + 1, shipment, request, userId))
                .collect(Collectors.toList());
    }

    private ShipmentRoute buildShipmentRoute(HubResponseDto hubResponseDto,
                                             int sequence,
                                             Shipment shipment,
                                             OrderDto request,
                                             String userId) {
        return ShipmentRoute.of(
                sequence,
                hubResponseDto.getOriginHubId(),
                hubResponseDto.getDestinationHubId(),
                request.getDeliveryAddress(),
                hubResponseDto.getDistanceKm(),
                hubResponseDto.getEstimatedMinutes(),
                userId,
                shipment
        );
    }

}
