package com.sparta_express.hub.infrastructure.map;

import lombok.Data;

import java.util.List;


@Data
public class NaverGeocodingResponse {

    private String status;
    private List<Address> addresses;

    public boolean isFailed() {

        return !status.equals("OK");
    }

    @Data
    public static class Address {
        private String x; // 경도
        private String y; // 위도

        public double getLatitude() {
            return Double.parseDouble(y);
        }

        public double getLongitude() {
            return Double.parseDouble(x);
        }
    }

}
