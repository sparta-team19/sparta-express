package com.sparta_express.hub.domain;


import org.locationtech.jts.geom.Point;


public interface MapApplication {
    HubToDestinationDTO searchRoute(Point origin, Point destination);

    Point searchGeometryPoint(String address);

}
