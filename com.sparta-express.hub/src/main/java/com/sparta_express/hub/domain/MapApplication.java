package com.sparta_express.hub.domain;


public interface MapApplication {
    HubToDestinationDTO searchRoute(Position origin, Position destination);

}
