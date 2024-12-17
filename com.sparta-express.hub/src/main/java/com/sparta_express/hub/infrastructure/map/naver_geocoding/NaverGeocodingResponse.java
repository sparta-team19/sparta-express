package com.sparta_express.hub.infrastructure.map.naver_geocoding;

import lombok.Data;

import java.util.List;


@Data
public class NaverGeocodingResponse {

    private List<Address> addresses;

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
