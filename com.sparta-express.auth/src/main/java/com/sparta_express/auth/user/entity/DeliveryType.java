package com.sparta_express.auth.user.entity;

public enum DeliveryType {
    HUB_DELIVERY_MANAGER("허브 배송 담당자"),
    COMPANY_DELIVERY_MANAGER("업체 배송 담당자");

    private final String deliveryType;

    DeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
