package com.sparta_express.order_shipment.application.dto;

import com.sparta_express.order_shipment.domain.entity.Order;
import com.sparta_express.order_shipment.domain.entity.Shipment;
import com.sparta_express.order_shipment.domain.entity.ShipmentStatusEnum;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateResponse {

    private UUID requesterId;
    private UUID receiverId;
    private UUID productId;
    private Integer quantity;
    private LocalDate dueDate;
    private String requestDetails;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Boolean isDelete;
    private List<shipmentDto> shipment;

    @Builder
    private OrderCreateResponse(UUID requesterId, UUID receiverId, UUID productId,
                                Integer quantity, LocalDate dueDate, String requestDetails,
                                String userId, LocalDateTime createdAt, LocalDateTime updatedAt,
                                String createdBy, String updatedBy, Boolean isDelete, List<shipmentDto> shipment) {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.productId = productId;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.requestDetails = requestDetails;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDelete = isDelete;
        this.shipment = shipment;
    }

    public static OrderCreateResponse from(Order order) {
        return OrderCreateResponse.builder()
                .requesterId(order.getRequesterId())
                .receiverId(order.getReceiverId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .dueDate(order.getDueDate())
                .requestDetails(order.getRequestDetails())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .createdBy(order.getCreatedBy())
                .updatedBy(order.getUpdatedBy())
                .isDelete(order.getIsDelete())
                .shipment(List.of(shipmentDto.from(order.getShipment())))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class shipmentDto {
        private UUID shipmentId;
        private ShipmentStatusEnum status;

        public static shipmentDto from(Shipment shipment) {
            return new shipmentDto(shipment.getId(), shipment.getStatus());
        }
    }

}
