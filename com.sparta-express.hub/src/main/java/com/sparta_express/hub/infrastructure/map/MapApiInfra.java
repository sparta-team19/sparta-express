package com.sparta_express.hub.infrastructure.map;

import com.sparta_express.hub.domain.MapApi;
import com.sparta_express.hub.domain.Position;

public interface MapApiInfra extends MapApi {

    Position searchPosition(String address);

}