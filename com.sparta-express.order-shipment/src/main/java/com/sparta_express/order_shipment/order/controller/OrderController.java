package com.sparta_express.order_shipment.order.controller;

import com.sparta_express.order_shipment.common.ResponseDataDto;
import com.sparta_express.order_shipment.common.ResponseMessageDto;
import com.sparta_express.order_shipment.common.ResponseStatus;
import com.sparta_express.order_shipment.external.UserClient;
import com.sparta_express.order_shipment.external.UserResponseDto;
import com.sparta_express.order_shipment.external.UserRole;
import com.sparta_express.order_shipment.order.dto.CreateOrderRequest;
import com.sparta_express.order_shipment.order.dto.OrderResponse;
import com.sparta_express.order_shipment.order.dto.UpdateOrderRequest;
import com.sparta_express.order_shipment.order.model.Order;
import com.sparta_express.order_shipment.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.sparta_express.order_shipment.external.UserRole.HUB_MANAGER;
import static com.sparta_express.order_shipment.external.UserRole.MASTER;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserClient userClient;

    // 주문 생성
    @PostMapping
    public ResponseEntity<ResponseDataDto<OrderResponse>> createOrder(
            @RequestHeader("X-User-Id") String email,
            @Valid @RequestBody CreateOrderRequest createOrderRequest) {
        Order order = orderService.createOrder(createOrderRequest);
        OrderResponse orderResponse = OrderResponse.from(order);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CREATE_SUCCESS, orderResponse));
    }

    // 주문 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<OrderResponse>>> getOrders() {
        List<OrderResponse> orders = orderService.getOrders().stream()
                .map(OrderResponse::from)
                .toList();
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIST_SUCCESS, orders));
    }

    // 주문 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataDto<OrderResponse>> getOrderById(
            @RequestHeader("X-User-Id") String email,
            @PathVariable UUID id) {
        UserResponseDto user = userClient.getUserByEmail(email).getBody().getData();
        Order order = orderService.getOrderById(id);

        OrderResponse orderResponse = OrderResponse.from(order);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DETAIL_SUCCESS, orderResponse));
    }

    // 주문 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataDto<OrderResponse>> updateOrder(
            @RequestHeader("X-User-Id") String email,
            @PathVariable UUID id,
            @RequestBody UpdateOrderRequest request) {
        UserResponseDto user = userClient.getUserByEmail(email).getBody().getData();
        if (!hasRole(user, MASTER, HUB_MANAGER)) {
            return ResponseEntity.status(403).body(new ResponseDataDto<>(ResponseStatus.VALIDATION_ERROR, null));
        }

        Order order = orderService.updateOrder(id, request);
        OrderResponse orderResponse = OrderResponse.from(order);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.UPDATE_SUCCESS, orderResponse));
    }

    // 주문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteOrder(
            @RequestHeader("X-User-Id") String email,
            @PathVariable UUID id) {
        UserResponseDto user = userClient.getUserByEmail(email).getBody().getData();
        if (!hasRole(user, MASTER, HUB_MANAGER)) {
            return ResponseEntity.status(403).body(new ResponseMessageDto(ResponseStatus.VALIDATION_ERROR));
        }

        orderService.deleteOrder(id, email);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_SUCCESS));
    }

    private boolean hasRole(UserResponseDto user, UserRole... allowedRoles) {
        for (UserRole role : allowedRoles) {
            if (user.getRole().getAuthority().equals(role.getAuthority())) {
                return true;
            }
        }
        return false;
    }

}