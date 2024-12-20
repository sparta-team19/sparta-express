package com.sparta_express.hub.domain;


public interface MapApi {
    HubToDestinationDTO searchRoute(Position origin, Position destination);

}
