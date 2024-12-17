package com.sparta_express.order_shipment.external;

public enum UserRole {

    MASTER("마스터 관리자"),
    HUB_MANAGER("허브 관리자"),
    DELIVERY_MANAGER("배송 담당자"),
    COMPANY_MANAGER("업체 담당자");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

}
