package com.sparta_express.hub.infrastructure.map;

import com.sparta_express.hub.domain.HubToDestinationDTO;
import com.sparta_express.hub.domain.MapApplication;
import org.locationtech.jts.geom.Point;

public class MapApplicationImpl implements MapApplication {


    @Override
    public Point searchGeometryPoint(String address) {
        return null;
    }

    @Override
    public HubToDestinationDTO searchRoute(Point origin, Point destination) {
        return null;
    }
}
