package com.sparta_express.order_shipment.presentation.controller;


import com.sparta_express.order_shipment.application.dto.OrderCreateResponse;
import com.sparta_express.order_shipment.application.dto.OrderResponse;
import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.application.dto.ResponseMessageDto;
import com.sparta_express.order_shipment.application.dto.ResponseStatus;
import com.sparta_express.order_shipment.application.service.OrderService;
import com.sparta_express.order_shipment.domain.entity.Order;
import com.sparta_express.order_shipment.presentation.dto.OrderRequest;
import com.sparta_express.order_shipment.presentation.dto.UpdateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.sparta_express.order_shipment.application.dto.ResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseDataDto<OrderCreateResponse>> createOrder(@Valid @RequestBody OrderRequest request,
                                                                            @RequestHeader(name = "X-Email") String email) {
        OrderCreateResponse response = orderService.createOrder(request.toDto(), email);
        return ResponseEntity.ok(new ResponseDataDto<>(ORDER_CREATE_SUCCESS, response));
    }

    // 주문 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<OrderResponse>>> getOrders() {
        List<OrderResponse> orders = orderService.getOrders().stream()
                .map(OrderResponse::from)
                .toList();
        return ResponseEntity.ok(new ResponseDataDto<>(LIST_SUCCESS, orders));
    }

    // 주문 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataDto<OrderResponse>> getOrderById(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        OrderResponse orderResponse = OrderResponse.from(order);
        return ResponseEntity.ok(new ResponseDataDto<>(DETAIL_SUCCESS, orderResponse));
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
            @RequestHeader("X-Email") String email,
            @PathVariable UUID id) {
        orderService.deleteOrder(id, email);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_SUCCESS));
    }

}
