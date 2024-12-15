package com.sparta_express.hub.domain.map;


import com.sparta_express.hub.domain.dto.HubToDestinationRoute;
import org.locationtech.jts.geom.Point;


public interface MapApplication {
    public HubToDestinationRoute searchRoute(Point origin, Point destination);
}
