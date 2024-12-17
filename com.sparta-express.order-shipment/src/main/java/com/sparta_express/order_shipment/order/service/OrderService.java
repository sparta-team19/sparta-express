package com.sparta_express.order_shipment.order.service;

import com.sparta_express.order_shipment.common.CustomException;
import com.sparta_express.order_shipment.common.ErrorType;
import com.sparta_express.order_shipment.order.dto.CreateOrderRequest;
import com.sparta_express.order_shipment.order.dto.UpdateOrderRequest;
import com.sparta_express.order_shipment.order.model.Order;
import com.sparta_express.order_shipment.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Order order = Order.of(
                request.getProductId(),
                request.getRequesterId(),
                request.getReceiverId(),
                request.getQuantity(),
                request.getDueDate(),
                request.getRequestDetails()
        );
        return orderRepository.save(order);
    }

    // 주문 목록 조회
    public List<Order> getOrders() {
        return orderRepository.findAllByIsDeleteFalse();
    }

    // 주문 상세 조회
    public Order getOrderById(UUID id) {
        return orderRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
    }

    // 주문 수정
    public Order updateOrder(UUID id, UpdateOrderRequest request) {
        Order order = orderRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        order.update(request.getQuantity(), request.getDueDate(), request.getRequestDetails(), request.getOrderStatus());
        return order;
    }

    // 주문 삭제
    @Transactional
    public void deleteOrder(UUID id, String email) {
        Order order = orderRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        order.delete(email);
    }

}