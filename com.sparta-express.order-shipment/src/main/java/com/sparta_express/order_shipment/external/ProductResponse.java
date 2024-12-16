package com.sparta_express.order_shipment.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Integer price;

}
