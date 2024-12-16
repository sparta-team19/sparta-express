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

    public static class Authority {
        public static final String MASTER = "ROLE_MANAGER";
        public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
        public static final String DELIVERY_MANAGER = "ROLE_DELIVERY_MANAGER";
        public static final String COMPANY_MANAGER = "ROLE_COMPANY_MANAGER";
    }

}
