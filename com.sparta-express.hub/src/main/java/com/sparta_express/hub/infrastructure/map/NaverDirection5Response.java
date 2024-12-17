package com.sparta_express.hub.infrastructure.map;


import lombok.Data;

import java.util.List;


@Data
public class NaverDirection5Response {

    private static final int SUCCESS_CODE = 0;
    private Route route;
    private int code;

    @Data
    public static class Route {

        private List<Info> traoptimal;

        @Data
        private static class Info {
            private Summary summary;

            @Data
            public static class Summary {
                private int distance;
                private int duration;
            }
        }
    }

    public boolean isFailed() {
        return code != SUCCESS_CODE;
    }

    public int getDistanceMeter() {
        return route.getTraoptimal().get(0).getSummary().getDistance();
    }

    public int getDurationMilliSeconds() {
        return route.getTraoptimal().get(0).getSummary().getDuration();
    }
}
