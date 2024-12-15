package com.sparta_express.order_shipment.order.service;

import com.sparta_express.company_product.external.Order;
import com.sparta_express.order_shipment.common.CustomException;
import com.sparta_express.order_shipment.common.ErrorType;
import com.sparta_express.order_shipment.external.Product;
import com.sparta_express.order_shipment.external.ProductRepository;
import com.sparta_express.order_shipment.external.User;
import com.sparta_express.order_shipment.external.UserRepository;
import com.sparta_express.order_shipment.order.dto.CreateOrderRequest;
import com.sparta_express.order_shipment.order.dto.UpdateOrderRequest;
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
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        Product product = productRepository.findByIdAndIsDeleteFalse(request.getProductId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));

        User requester = userRepository.findById(request.getRequesterId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));

        Order order = Order.of(
                product,
                requester,
                receiver,
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