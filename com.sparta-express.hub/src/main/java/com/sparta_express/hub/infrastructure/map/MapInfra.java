package com.sparta_express.hub.infrastructure.map;

import com.sparta_express.hub.domain.MapApplication;
import com.sparta_express.hub.domain.Position;

public interface MapInfra extends MapApplication {

    Position searchGeometryPoint(String address);

}