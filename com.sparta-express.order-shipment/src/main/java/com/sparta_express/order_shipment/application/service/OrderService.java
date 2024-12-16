package com.sparta_express.order_shipment.application.service;

import com.sparta_express.order_shipment.application.dto.OrderDto;
import com.sparta_express.order_shipment.application.dto.OrderCreateResponse;
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
import com.sparta_express.order_shipment.infrastructure.dto.CompanyResponseDto;
import com.sparta_express.order_shipment.infrastructure.dto.HubResponseDto;
import com.sparta_express.order_shipment.infrastructure.dto.ProductResponseDto;
import com.sparta_express.order_shipment.infrastructure.dto.UserResponseDto;
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
//    private final HubClient hubClient;
    private final UserClient userClient;

    public OrderCreateResponse createOrder(OrderDto request, String email) {

        /*
        todo: 각 api 담당자가 구현 완료하면 응답 값 맞춰서 수정 예정
         * 1. 사용자 조회
         * 2. 업체 조회 -> 업체 체크
         * 3. 상품 조회 -> 상품 체크
         * 4. 상품 재고 감소
         * 5. 허브 경로 조회
         * 6. 배송 담당자 조회 (배송담당자 애플리케이션 내에서 구현):
                    - redis sortedset 이용해서 round-robin 형식으로 순번 낮은 사람 가져오기
                    - 해당 api 호출 시 허브 간 경로의 개수만큼 조회하여 가져오기
         * */

        boolean isProductStockDecreased = false;

        try {
            UserResponseDto userResponse = userClient.getUser(email).getData();
            log.info("### Fetching userResponse details : {}", userResponse.toString());
            CompanyResponseDto requesterResponse = companyProductClient.getCompanyById(request.getRequesterId()).getData();
            log.info("### Fetching requesterResponse details : {}", requesterResponse.toString());
            CompanyResponseDto receiverResponse = companyProductClient.getCompanyById(request.getReceiverId()).getData();
            log.info("### Fetching receiverResponse details : {}", receiverResponse.toString());
            ProductResponseDto productResponse = companyProductClient.getProductById(request.getProductId()).getData();
            log.info("### Fetching productResponse details : {}", productResponse.toString());
            companyProductClient.reduceStock(productResponse.getId(), request.getQuantity());
            isProductStockDecreased = true;
//            List<HubResponseDto> hubResponseList = hubClient.getHubRoutesInfo(requesterResponse.getHubID(), receiverResponse.getHubID()).getData();
//            // 6.
//            UserResponseDto managerList = userClient.getDeliveryManager().getData();
//
//            Order order = buildOrder(requesterResponse, receiverResponse, productResponse, userResponse, request);
//            Shipment shipment = buildShipment(requesterResponse, receiverResponse, userResponse, request, order);
//            order.updateShipment(shipment);
//            orderRepository.save(order);
//            shipmentRouteRepository.saveAll(createShipmentRoutes(hubResponseList, managerList, shipment, request, email));
//
//            return OrderCreateResponse.from(order);
            return null;

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

//    private List<ShipmentRoute> createShipmentRoutes(List<HubResponseDto> hubResponseList,
//                                                     UserResponseDto managerList,
//                                                     Shipment shipment,
//                                                     OrderDto request,
//                                                     String userId) {
//        return IntStream.range(0, hubResponseList.size())
//                .mapToObj(i -> buildShipmentRoute(hubResponseList.get(i), i + 1, managerList, shipment, request, userId))
//                .collect(Collectors.toList());
//    }

//    private ShipmentRoute buildShipmentRoute(HubResponseDto hubResponseDto,
//                                             int sequence,
//                                             UserResponseDto managerList,
//                                             Shipment shipment,
//                                             OrderDto request,
//                                             String userId) {
//        return ShipmentRoute.of(
//                sequence,
//                hubResponseDto.getOriginHubId(),
//                hubResponseDto.getDestinationHubId(),
//                request.getDeliveryAddress(),
//                hubResponseDto.getEstimatedDistance(),
//                hubResponseDto.getEstimatedTime(),
//                managerList.getDeliveryManagerId(),
//                userId,
//                shipment
//        );
//    }

//    private List<ShipmentRoute> createShipmentRoutes(List<HubResponseDto> hubResponseList, UserResponseDto managerList,
//                                                     Shipment shipment, OrderDto request, String userId) {
//        List<ShipmentRoute> shipmentRoutes = new ArrayList<>();
//        for(int i = 0; i < hubResponseList.size(); i++) {
//            HubResponseDto hubResponseDto = hubResponseList.get(i);
//            ShipmentRoute shipmentRoute = ShipmentRoute.of(
//                    i + 1,
//                    hubResponseDto.getOriginHubId(),
//                    hubResponseDto.getDestinationHubId(),
//                    request.getDeliveryAddress(),
//                    hubResponseDto.getEstimatedDistance(),
//                    hubResponseDto.getEstimatedTime(),
//                    managerList.getDeliveryManagerId(),
//                    userId,
//                    shipment
//            );
//            shipmentRoutes.add(shipmentRoute);
//        }
//        return shipmentRoutes;
//    }

}
