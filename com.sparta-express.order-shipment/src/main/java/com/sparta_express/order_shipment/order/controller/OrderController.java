package com.sparta_express.order_shipment.order.controller;

import com.sparta_express.company_product.external.Order;
import com.sparta_express.order_shipment.common.ResponseDataDto;
import com.sparta_express.order_shipment.common.ResponseMessageDto;
import com.sparta_express.order_shipment.common.ResponseStatus;
import com.sparta_express.order_shipment.order.dto.CreateOrderRequest;
import com.sparta_express.order_shipment.order.dto.OrderResponse;
import com.sparta_express.order_shipment.order.dto.UpdateOrderRequest;
import com.sparta_express.order_shipment.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<ResponseDataDto<OrderResponse>> createOrder(
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
    public ResponseEntity<ResponseDataDto<OrderResponse>> getOrderById(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        OrderResponse orderResponse = OrderResponse.from(order);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DETAIL_SUCCESS, orderResponse));
    }

    // 주문 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataDto<OrderResponse>> updateOrder(
            @PathVariable UUID id,
            @RequestBody UpdateOrderRequest request) {
        Order order = orderService.updateOrder(id, request);
        OrderResponse orderResponse = OrderResponse.from(order);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.UPDATE_SUCCESS, orderResponse));
    }

    // 주문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteOrder(
            @RequestHeader("X-User-Id") String email,
            @PathVariable UUID id) {
        orderService.deleteOrder(id, email);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_SUCCESS));
    }

}